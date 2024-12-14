package com.aopr.authentication_presentation.events

sealed class RegistrationUiEvents {
    data object NavigateToHomeScreen:RegistrationUiEvents()
    data object NavigateToSignInScreen:RegistrationUiEvents()
}