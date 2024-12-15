package com.aopr.authentication_presentation.model

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aopr.authentication_presentation.events.RegistrationEvents
import com.aopr.authentication_presentation.events.RegistrationUiEvents
import com.aopr.shared_domain.Responses
import com.aopr.shared_domain.inter.HomeUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class RegistrationViewModel(private val homeUseCase: HomeUseCase) : ViewModel() {

    private val _userName = MutableStateFlow<String>("")
    val userName: StateFlow<String> = _userName

    private val _gmail = MutableStateFlow<String>("")
    val gmail: StateFlow<String> = _gmail

    private val _password = MutableStateFlow<String>("")
    val password: StateFlow<String> = _password

    private val _event = MutableSharedFlow<RegistrationUiEvents>()
    val event = _event.asSharedFlow()

    private fun registerUser(gmail: String, password: String) {
        homeUseCase.registerUser(gmail, password).onEach { result ->
            when (result) {
                is Responses.Error -> {
                }

                is Responses.Loading -> {

                }

                is Responses.Success -> {

                }
            }

        }.launchIn(viewModelScope)
    }

    fun onEvent(events: RegistrationEvents) {
        when (events) {
            RegistrationEvents.RegisterUser -> {
                viewModelScope.launch {
                    registerUser(_gmail.value, _password.value)
                  //  _event.emit(RegistrationUiEvents.NavigateToHomeScreen)
                }
            }

            is RegistrationEvents.UpdatePassword -> {
                _password.value = events.password
            }

            is RegistrationEvents.UpdateUserName -> {
                _userName.value = events.name
            }

            is RegistrationEvents.UpdateGmail -> {
                _gmail.value = events.gmail
            }
        }
    }

}