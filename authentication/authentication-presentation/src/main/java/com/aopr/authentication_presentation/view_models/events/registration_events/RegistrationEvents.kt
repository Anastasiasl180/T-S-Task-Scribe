package com.aopr.authentication_presentation.view_models.events.registration_events

import android.content.Context
import android.net.Uri

sealed interface RegistrationEvents {
    data class UpdateUserName(val name: String) : RegistrationEvents
    data object RegisterUser : RegistrationEvents
    data object NavigateToLogInUser : RegistrationEvents
    data class UpdateGmail(val gmail: String) : RegistrationEvents
    data class UpdateUserImage(val image: Uri?,val context: Context) : RegistrationEvents
    data class UpdatePassword(val password: String) : RegistrationEvents
}
