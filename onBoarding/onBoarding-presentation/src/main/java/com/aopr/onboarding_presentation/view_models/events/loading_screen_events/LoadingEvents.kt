package com.aopr.onboarding_presentation.view_models.events.loading_screen_events

sealed interface LoadingEvents {
    data object NavigateToFirstOnBoardingScreenOrHomeScreen: LoadingEvents
}