package com.aopr.home.home_screen.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aopr.home.home_screen.viewModel.events.HomeEvent
import com.aopr.home.home_screen.viewModel.events.HomeUiEvents
import com.aopr.shared_ui.util.ViewModelKit
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class HomeViewModel : ViewModelKit<HomeEvent,HomeUiEvents>() {

    private val _event = MutableSharedFlow<UiEvent>()
    val uiEvents = _event.asSharedFlow()

    sealed class UiEvent {
        data object NavigateToAllNotes : UiEvent()
    }


    override fun onEvent(event: HomeEvent) {
        when (event) {
            HomeEvent.NavigateToAllNotes -> {
                viewModelScope.launch {
                    _event.emit(UiEvent.NavigateToAllNotes)
                }
            }
        }
    }


}