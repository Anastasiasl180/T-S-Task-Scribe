package com.aopr.onboarding_presentation.view_models.events.second_screen_events

sealed interface SecondScreenEvents {
    data object NavigateToHomeScreen: SecondScreenEvents
    data object NavigateToBack: SecondScreenEvents
}