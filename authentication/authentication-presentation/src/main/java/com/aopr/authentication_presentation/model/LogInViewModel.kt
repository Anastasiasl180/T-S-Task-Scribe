package com.aopr.authentication_presentation.model

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aopr.authentication_domain.interactors.AuthenticationUseCase
import com.aopr.authentication_presentation.events.log_in_events.LogInEvents
import com.aopr.authentication_presentation.events.log_in_events.LogInUiEvents
import com.aopr.firebase_data.helpers.Helpers
import com.aopr.firebase_domain.firestore_user_data.FireUser
import com.aopr.notes_domain.interactors.NotesUseCase
import com.aopr.shared_domain.Responses
import com.aopr.tasks_domain.interactors.TasksUseCase
import com.example.bookmarks_domain.interactors.BookmarksUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class LogInViewModel(
    private val authenticationUseCase: AuthenticationUseCase,
    private val bookmarksUseCase:BookmarksUseCase,
    private val notesUseCase:NotesUseCase,
    private val tasksUseCase: TasksUseCase,
    private val user: FireUser
) : ViewModel() {

    private val _gmail = MutableStateFlow("")
    val gmail: StateFlow<String> = _gmail

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password

    private val _user = MutableStateFlow(FireUser())
    val userFire: StateFlow<FireUser> = _user

    private val _event = MutableSharedFlow<LogInUiEvents>()
    val event = _event.asSharedFlow()

    private fun logInUser(gmail: String, password: String) {
        authenticationUseCase.logInUser(gmail, password).onEach { result ->
            when (result) {
                is Responses.Error -> {

                }

                is Responses.Loading -> {

                }

                is Responses.Success -> {

                    result.data?.let {
                        user.userId = result.data
                        Log.wtf("loginSuccess",it.toString())
                        Log.wtf("USER",userFire.value.userId.toString())
                        Helpers.FirebaseHelper.getAllUserData(it,bookmarksUseCase,tasksUseCase,notesUseCase) }
                }
            }

        }.launchIn(viewModelScope)
    }

    private fun retrieveUserById(id: String) {
        authenticationUseCase.retrieveUserById(id).onEach { result ->
            when (result) {
                is Responses.Error -> {
                }

                is Responses.Loading -> {

                }

                is Responses.Success -> {
                    result.data?.collect() { it ->
                        if (it != null) {
                            _user.value = it
                        }
                    }
                }
            }

        }.launchIn(viewModelScope)
    }


    fun onEvent(events: LogInEvents) {
        when (events) {
            LogInEvents.LogInUser -> {
                viewModelScope.launch {
                    logInUser(_gmail.value, _password.value)
                    onEvent(LogInEvents.NavigateToHome)
                }
            }

            LogInEvents.NavigateToHome -> {
                viewModelScope.launch {
                    _event.emit(LogInUiEvents.NavigateToHomeScreen)
                }
            }

            is LogInEvents.UpdateGmail -> {
                _gmail.value = events.gmail
            }

            is LogInEvents.UpdatePassword -> {
                _password.value = events.password
            }

            is LogInEvents.UpdateUserName -> {

            }
        }
    }
}