package com.aopr.onboarding_presentation.view_models.events.second_screen_events

sealed class SecondScreenUiEvents {
    data object NavigateToHome: SecondScreenUiEvents()
    data object NavigateBack: SecondScreenUiEvents()
}