package com.aopr.authentication_presentation.events.registration_events

sealed class RegistrationUiEvents {
    data object NavigateToHomeScreen: RegistrationUiEvents()
    data object NavigateToLogInScreen: RegistrationUiEvents()
}