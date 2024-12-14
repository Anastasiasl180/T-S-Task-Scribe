package com.aopr.onboarding_presentation.events.first_screen_events

sealed interface FirstScreenEvents {
    data object NavigateToSecondScreen: FirstScreenEvents
}