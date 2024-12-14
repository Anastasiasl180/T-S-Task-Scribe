package com.aopr.onboarding_presentation.events.loading_screen_events



sealed class LoadingUiEvents {
    data object NavigateToFirstOnBoardingScreenOrHomeScreen:LoadingUiEvents()
}