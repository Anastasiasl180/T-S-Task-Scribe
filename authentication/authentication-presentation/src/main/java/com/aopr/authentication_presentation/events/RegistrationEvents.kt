package com.aopr.authentication_presentation.events

sealed interface RegistrationEvents {
    data class UpdateUserName(val name:String):RegistrationEvents
    data object RegisterUser:RegistrationEvents
    data class UpdateGmail(val gmail:String):RegistrationEvents
    data class UpdatePassword(val password:String):RegistrationEvents
}