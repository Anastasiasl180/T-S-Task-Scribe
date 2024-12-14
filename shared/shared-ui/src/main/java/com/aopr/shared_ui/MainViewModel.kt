package com.aopr.shared_ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aopr.shared_domain.Responses
import com.aopr.shared_domain.colors_for_theme.Themes
import com.aopr.shared_domain.inter.HomeUseCase
import com.aopr.shared_domain.inter.SettingsDto
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class MainViewModel(private val homeUseCase: HomeUseCase) : ViewModel() {

    private val _chosenTheme = MutableStateFlow<Themes>(Themes.VIOLET)
    val chosenTheme: StateFlow<Themes> = _chosenTheme

    private val _isBottomBarShowed = MutableStateFlow(false)
    val isBottomBarShowed: StateFlow<Boolean> = _isBottomBarShowed

    private val _event = MutableSharedFlow<UiEvent>()
    val event = _event.asSharedFlow()

    sealed class UiEvent {
        data object NavigateToHomeScreen : UiEvent()
        data object NavigateToAiScreen : UiEvent()
        data object NavigateToDashBoardScreen : UiEvent()
    }

    init {
        getTheme()
    }

    private fun updateTheme(theme: Themes) {
        homeUseCase.updateTheme(theme).onEach { result ->
            when (result) {
                is Responses.Error -> {

                }

                is Responses.Loading -> {

                }

                is Responses.Success -> {

                }
            }

        }.launchIn(viewModelScope)
    }

    private fun getTheme() {
        homeUseCase.getTheme().onEach { result ->
            when (result) {
                is Responses.Error -> {

                }

                is Responses.Loading -> {
                }

                is Responses.Success -> {
                    result.data?.theme.let {
                        if (it != null) {
                              _chosenTheme.value = it
                        }
                    }
                }
            }

        }.launchIn(viewModelScope)
    }

    fun onEvent(event: MainEvent) {
        when (event) {
            MainEvent.NavigateToAiScreen -> {
                viewModelScope.launch {
                    _event.emit(UiEvent.NavigateToAiScreen)
                }
            }

            MainEvent.NavigateToDashBoardScreen -> {
                viewModelScope.launch {
                    _event.emit(UiEvent.NavigateToDashBoardScreen)
                }
            }

            MainEvent.NavigateToHomeScreen -> {
                viewModelScope.launch {
                    _event.emit(UiEvent.NavigateToHomeScreen)
                    onEvent(MainEvent.ShowBottomBar)
                }
            }

            is MainEvent.ChosenTheme -> {
                viewModelScope.launch {
                   _chosenTheme.value = event.theme
                    updateTheme(event.theme)
                }
            }

            MainEvent.ShowBottomBar -> {
                _isBottomBarShowed.value = true
            }
        }

    }

    sealed interface MainEvent {
        data object NavigateToHomeScreen : MainEvent
        data object NavigateToAiScreen : MainEvent
        data object NavigateToDashBoardScreen : MainEvent
        data object ShowBottomBar : MainEvent
        data class ChosenTheme(val theme: Themes) : MainEvent
    }

}