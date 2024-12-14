package com.aopr.onboarding_presentation.events.first_screen_events

sealed class FirstScreenUiEvents {
    data object NavigateToSecondScreen: FirstScreenUiEvents()
}