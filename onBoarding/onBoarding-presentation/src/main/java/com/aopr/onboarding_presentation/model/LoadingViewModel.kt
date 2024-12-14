package com.aopr.onboarding_presentation.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aopr.onboarding_presentation.events.loading_screen_events.LoadingEvents
import com.aopr.onboarding_presentation.events.loading_screen_events.LoadingUiEvents
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class LoadingViewModel : ViewModel() {

    private val _event = MutableSharedFlow<LoadingUiEvents>()
    val event = _event.asSharedFlow()

    fun onEvent(events: LoadingEvents) {
        when (events) {
            LoadingEvents.NavigateToFirstOnBoardingScreenOrHomeScreen -> {
                viewModelScope.launch {
                    _event.emit(LoadingUiEvents.NavigateToFirstOnBoardingScreenOrHomeScreen)
                }
            }
        }
    }

}