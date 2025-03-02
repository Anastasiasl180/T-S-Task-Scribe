package com.aopr.onboarding_presentation.view_models.events.first_screen_events

sealed class FirstScreenUiEvents {
    data object NavigateToSecondScreen: FirstScreenUiEvents()
}