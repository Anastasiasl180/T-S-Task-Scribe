package com.aopr.authentication_presentation.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface AuthenticationRoutes {

    @Serializable
    data object RegistrationScreen:AuthenticationRoutes

    @Serializable
    data object LogInScreen:AuthenticationRoutes

}