package com.aopr.onboarding_presentation.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface OnBoardingNavRoutes {

    @Serializable
    data object FirstScreen:OnBoardingNavRoutes

    @Serializable
    data object SecondScreen:OnBoardingNavRoutes

}