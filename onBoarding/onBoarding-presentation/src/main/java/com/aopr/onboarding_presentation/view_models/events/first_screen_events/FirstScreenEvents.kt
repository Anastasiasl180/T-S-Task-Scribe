package com.aopr.onboarding_presentation.view_models.events.first_screen_events

sealed interface FirstScreenEvents {
    data object NavigateToSecondScreen: FirstScreenEvents
}