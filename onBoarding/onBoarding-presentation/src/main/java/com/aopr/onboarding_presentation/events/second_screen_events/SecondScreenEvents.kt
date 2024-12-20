package com.aopr.onboarding_presentation.events.second_screen_events

sealed interface SecondScreenEvents {
    data object NavigateToHomeScreen:SecondScreenEvents
    data object NavigateToBack:SecondScreenEvents
}