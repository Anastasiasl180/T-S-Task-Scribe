package com.aopr.authentication_presentation.view_models

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aopr.authentication_domain.interactors.AuthenticationUseCase
import com.aopr.authentication_presentation.view_models.events.log_in_events.LogInEvents
import com.aopr.authentication_presentation.view_models.events.log_in_events.LogInUiEvents
import com.aopr.firebase_data.helpers.Helpers
import com.aopr.firebase_domain.firestore_user_data.FireUser
import com.aopr.notes_domain.interactors.NotesUseCase
import com.aopr.shared_domain.Responses
import com.aopr.shared_domain.interactors.UserDataForFireBase
import com.aopr.tasks_domain.interactors.TasksUseCase
import com.example.bookmarks_domain.interactors.BookmarksUseCase
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
    private val bookmarksUseCase: BookmarksUseCase,
    private val notesUseCase: NotesUseCase,
    private val tasksUseCase: TasksUseCase,
    private val user: FireUser
) : ViewModel() {

    private val _gmail = MutableStateFlow("")
    val gmail: StateFlow<String> = _gmail

    private val _gmailForResettingPassword = MutableStateFlow("")
    val gmailForResettingPassword: StateFlow<String> = _gmailForResettingPassword

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password

    private val _user = MutableStateFlow(FireUser())
    val userFire: StateFlow<FireUser> = _user

    private val _isDialogShowed = mutableStateOf(false)
    val isDialogShowed: State<Boolean> = _isDialogShowed

    private val _event = MutableSharedFlow<LogInUiEvents>()
    val event = _event.asSharedFlow()


   /* private fun setUserDataIntoDB(userDataForFireBase: UserDataForFireBase) {
        authenticationUseCase.setUserDataIntoBD(userDataForFireBase).onEach { result ->
            when (result) {
                is Responses.Error -> {

                }

                is Responses.Loading -> {

                }

                is Responses.Success -> {

                }
            }
        }
    }
*/
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
                        Helpers.FirebaseHelper.getAllUserData(
                            it,
                            bookmarksUseCase,
                            tasksUseCase,
                            notesUseCase
                        )
                        val userDataForFireBase = user.userName?.let { it1 ->
                            UserDataForFireBase(
                                it1, user.userPicture
                            )
                        }
                       // setUserDataIntoDB(userDataForFireBase!!)
                    }
                }
            }

        }.launchIn(viewModelScope)
    }

    private fun sendRestorePasswordCode(gmail: String) {
        authenticationUseCase.sendResetPasswordCode(gmail).onEach { result ->
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


            is LogInEvents.SendResetPasswordCode -> {
                viewModelScope.launch {
                    sendRestorePasswordCode(events.gmail)
                }
            }

            LogInEvents.HideAlertDialog -> {
                _isDialogShowed.value = false
            }

            LogInEvents.ShowAlertDialog -> {
                _isDialogShowed.value = true
            }

            is LogInEvents.UpdateGmailForResettingPassword -> {
                _gmailForResettingPassword.value = events.gmail
            }
        }
    }
}