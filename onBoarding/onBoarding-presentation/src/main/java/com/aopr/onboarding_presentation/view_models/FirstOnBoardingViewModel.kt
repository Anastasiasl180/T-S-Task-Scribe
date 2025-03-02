package com.aopr.onboarding_presentation.view_models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aopr.onboarding_presentation.view_models.events.first_screen_events.FirstScreenEvents
import com.aopr.onboarding_presentation.view_models.events.first_screen_events.FirstScreenUiEvents
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class FirstOnBoardingViewModel : ViewModel() {
    private val _event = MutableSharedFlow<FirstScreenUiEvents>()
    val event = _event.asSharedFlow()

    fun onEvent(event: FirstScreenEvents) {
        when (event) {
            FirstScreenEvents.NavigateToSecondScreen -> {
                viewModelScope.launch {
                    _event.emit(FirstScreenUiEvents.NavigateToSecondScreen)
                }
            }
        }
    }

}