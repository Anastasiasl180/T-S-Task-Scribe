package com.aopr.authentication_presentation.events.log_in_events

sealed interface LogInEvents {
    data class UpdateUserName(val name: String): LogInEvents
    data object LogInUser:LogInEvents
    data object NavigateToHome:LogInEvents
    data class UpdateGmail(val gmail: String):LogInEvents
    data class UpdatePassword(val password: String):LogInEvents
}