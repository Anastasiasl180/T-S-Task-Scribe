package com.aopr.shared_ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aopr.shared_ui.colors_for_theme.Themes
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class MainViewModel : ViewModel() {

    private val _chosenTheme = MutableStateFlow<Themes>(Themes.VIOLET)
    val chosenTheme: StateFlow<Themes> = _chosenTheme

    private val _event = MutableSharedFlow<UiEvent>()
    val event = _event.asSharedFlow()

    sealed class UiEvent {
        data object NavigateToHomeScreen : UiEvent()
        data object NavigateToAiScreen : UiEvent()
        data object NavigateToDashBoardScreen : UiEvent()
    }
    fun onEvent(event:MainEvent) {
        when(event){
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
                }
            }

            is MainEvent.ChosenTheme -> {
                _chosenTheme.value = event.theme
            }
        }

    }

    sealed interface MainEvent {
        data object NavigateToHomeScreen : MainEvent
        data object NavigateToAiScreen : MainEvent
        data object NavigateToDashBoardScreen : MainEvent
        data class ChosenTheme(val theme: Themes): MainEvent
    }

}