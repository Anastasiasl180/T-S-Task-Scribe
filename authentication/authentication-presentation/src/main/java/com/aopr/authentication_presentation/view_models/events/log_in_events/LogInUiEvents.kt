package com.aopr.authentication_presentation.view_models.events.log_in_events

sealed class LogInUiEvents {
    data object NavigateToHomeScreen: LogInUiEvents()
}