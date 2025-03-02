package com.aopr.authentication_presentation.view_models.events.log_in_events

sealed interface LogInEvents {
    data object LogInUser: LogInEvents
    data object NavigateToHome: LogInEvents
    data object ShowAlertDialog: LogInEvents
    data object HideAlertDialog: LogInEvents
    data class SendResetPasswordCode(val gmail: String): LogInEvents
    data class UpdateGmail(val gmail: String): LogInEvents
    data class UpdateGmailForResettingPassword(val gmail: String): LogInEvents
    data class UpdatePassword(val password: String): LogInEvents
}