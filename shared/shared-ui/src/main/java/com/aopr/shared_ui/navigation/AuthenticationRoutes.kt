package com.aopr.shared_ui.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface AuthenticationRoutes {

    @Serializable
    data object RegistrationScreen: AuthenticationRoutes

    @Serializable
    data object LogInScreen: AuthenticationRoutes

}