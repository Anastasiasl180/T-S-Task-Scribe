package com.aopr.authentication_presentation.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aopr.authentication_presentation.events.RegistrationEvents
import com.aopr.authentication_presentation.events.RegistrationUiEvents
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class RegistrationViewModel : ViewModel() {

    private val _userName = MutableStateFlow<String>("")
    val userName: StateFlow<String> = _userName

    private val _password = MutableStateFlow<String>("")
    val password: StateFlow<String> = _password

    private val _event = MutableSharedFlow<RegistrationUiEvents>()
    val event = _event.asSharedFlow()

    fun onEvent(events: RegistrationEvents) {
        when (events) {
            RegistrationEvents.RegisterUser -> {
                viewModelScope.launch {
                    _event.emit(RegistrationUiEvents.NavigateToHomeScreen)
                }
            }

            is RegistrationEvents.UpdatePassword -> {
                _password.value = events.password
            }

            is RegistrationEvents.UpdateUserName -> {
                _userName.value = events.name
            }
        }
    }

}