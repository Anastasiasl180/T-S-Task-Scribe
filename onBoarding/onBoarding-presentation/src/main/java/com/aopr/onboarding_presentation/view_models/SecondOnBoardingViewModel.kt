package com.aopr.onboarding_presentation.view_models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aopr.onboarding_presentation.view_models.events.second_screen_events.SecondScreenEvents
import com.aopr.onboarding_presentation.view_models.events.second_screen_events.SecondScreenUiEvents
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class SecondOnBoardingViewModel : ViewModel() {

    private val _event = MutableSharedFlow<SecondScreenUiEvents>()
    val event = _event.asSharedFlow()

    fun onEvent(event: SecondScreenEvents) {
        when (event) {
            SecondScreenEvents.NavigateToHomeScreen -> {
                viewModelScope.launch {
                    _event.emit(SecondScreenUiEvents.NavigateToHome)
                }
            }

            SecondScreenEvents.NavigateToBack -> {
                viewModelScope.launch {
                    _event.emit(SecondScreenUiEvents.NavigateBack)
                }
            }
        }
    }

}