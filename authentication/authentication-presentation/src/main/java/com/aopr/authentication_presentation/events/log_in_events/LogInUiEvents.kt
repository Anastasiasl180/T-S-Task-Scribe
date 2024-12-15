package com.aopr.authentication_presentation.events.log_in_events

sealed class LogInUiEvents {
    data object NavigateToHomeScreen:LogInUiEvents()
}