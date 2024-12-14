package com.aopr.onboarding_presentation.events.loading_screen_events

sealed interface LoadingEvents {
    data object NavigateToFirstOnBoardingScreenOrHomeScreen:LoadingEvents
}