package com.aopr.onboarding_presentation.view_models.events.loading_screen_events



sealed class LoadingUiEvents {
    data object NavigateToFirstOnBoardingScreenOrHomeScreen: LoadingUiEvents()
}