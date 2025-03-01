package com.aopr.shared_ui.util.global_view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aopr.shared_domain.Responses
import com.aopr.shared_domain.colors_for_theme.Themes
import com.aopr.shared_domain.inter.GlobalUseCase
import com.aopr.shared_ui.util.global_view_model.events.GlobalEvents
import com.aopr.shared_ui.util.global_view_model.events.GlobalUiEvents
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class GlobalViewModel(private val globalUseCase: GlobalUseCase) : ViewModel() {

    private val _isFirstLaunch = MutableStateFlow(true)
    val isFirstLaunch: StateFlow<Boolean> = _isFirstLaunch

    private val _userId = MutableStateFlow<String?>(null)
    val userId: StateFlow<String?> = _userId

    private val _isBottomBarMoved = MutableStateFlow<Boolean>(false)
    val isBottomBarMoved: StateFlow<Boolean> = _isBottomBarMoved

    private val _chosenTheme = MutableStateFlow<Themes>(Themes.VIOLET)
    val chosenTheme: StateFlow<Themes> = _chosenTheme

    private val _isBottomBarShowed = MutableStateFlow(false)
    val isBottomBarShowed: StateFlow<Boolean> = _isBottomBarShowed

    private val _event = MutableSharedFlow<GlobalUiEvents>()
    val event = _event.asSharedFlow()


    init {
        viewModelScope.launch {
            launch {
                getFirstLaunch()
            }
            launch {
                getTheme()
            }
        }


    }


    private fun getFirstLaunch() {
        globalUseCase.getIsFirstLaunch().onEach { result ->
            when (result) {
                is Responses.Error -> {

                }

                is Responses.Loading -> {

                }

                is Responses.Success -> {
                    result.data?.isFirstLaunch.let { it ->
                        if (it != null) {
                            _isFirstLaunch.value = it
                        }

                    }

                }
            }

        }.launchIn(viewModelScope)
    }

    private fun updateIsFirstLaunch(isFirstLaunch: Boolean) {
        globalUseCase.updateIsFirstLaunch(isFirstLaunch).onEach { result ->
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

    private fun updateTheme(theme: Themes) {
        globalUseCase.updateTheme(theme).onEach { result ->
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
        globalUseCase.getTheme().onEach { result ->
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

    fun onEvent(event: GlobalEvents) {
        when (event) {
            GlobalEvents.NavigateToAiScreen -> {
                viewModelScope.launch {
                    _event.emit(GlobalUiEvents.NavigateToAiScreen)
                }
            }

            GlobalEvents.NavigateToDashBoardScreen -> {
                viewModelScope.launch {
                    _event.emit(GlobalUiEvents.NavigateToDashBoardScreen)
                }
            }

            GlobalEvents.NavigateToHomeScreen -> {
                viewModelScope.launch {
                    _event.emit(GlobalUiEvents.NavigateToHomeScreen)
                    onEvent(GlobalEvents.ShowBottomBar)
                }
            }

            is GlobalEvents.ChosenTheme -> {
                viewModelScope.launch {
                    _chosenTheme.value = event.theme
                    updateTheme(event.theme)
                }
            }

            GlobalEvents.ShowBottomBar -> {
                _isBottomBarShowed.value = true
            }

            GlobalEvents.SetFirstLaunchToFalse -> {
                viewModelScope.launch {
                    updateIsFirstLaunch(false)
                }
            }

            is GlobalEvents.ToMoveBottomBar -> {
                if (event.move) {
                    _isBottomBarMoved.value = true
                } else {
                    _isBottomBarMoved.value = false
                }
            }

            GlobalEvents.NavigateBack -> {
                viewModelScope.launch {
                    _event.emit(GlobalUiEvents.NavigateBack)
                }
            }
        }

    }


}