package com.aopr.authentication_presentation.view_models

import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.util.Base64
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aopr.authentication_domain.interactors.AuthenticationUseCase
import com.aopr.authentication_presentation.view_models.events.registration_events.RegistrationEvents
import com.aopr.authentication_presentation.view_models.events.registration_events.RegistrationUiEvents
import com.aopr.firebase_domain.firestore_user_data.FireUser
import com.aopr.shared_domain.Responses
import com.aopr.shared_domain.interactors.UserDataForFireBase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel
import java.io.ByteArrayOutputStream

@KoinViewModel
class RegistrationViewModel(
    private val authenticationUseCase: AuthenticationUseCase,
    private var userr: FireUser
) :
    ViewModel() {

    private val _userName = MutableStateFlow("")
    val userName: StateFlow<String> = _userName

    private val _gmail = MutableStateFlow("")
    val gmail: StateFlow<String> = _gmail

    private val _selectedImage = MutableStateFlow<Uri?>(null)
    val selectedImage: StateFlow<Uri?> = _selectedImage

    private val _bitmapImage = MutableStateFlow<String?>(null)
    val bitmapImage: StateFlow<String?> = _bitmapImage

    private val _userID = MutableStateFlow<String?>(null)
    val userID: StateFlow<String?> = _userID

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password

    private val _event = MutableSharedFlow<RegistrationUiEvents>()
    val event = _event.asSharedFlow()


    private fun saveUser(user: FireUser) {
        authenticationUseCase.saveUser(user).onEach { result ->
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


    private fun registerUser(gmail: String, password: String) {
        authenticationUseCase.registerUser(gmail, password).onEach { result ->
            when (result) {
                is Responses.Error -> {
                }

                is Responses.Loading -> {

                }

                is Responses.Success -> {
                    userr.userId = result.data
                    userr.userName = _userName.value
                    userr.userPicture = _bitmapImage.value
                    val user = UserDataForFireBase(
                        _userName.value, profileImage = _bitmapImage.value
                    )
                    saveUser(userr)
                //   setUserDataIntoDB(user)
                }
            }

        }.launchIn(viewModelScope)
    }


    @RequiresApi(Build.VERSION_CODES.P)
    fun onEvent(events: RegistrationEvents) {
        when (events) {
            RegistrationEvents.RegisterUser -> {
                viewModelScope.launch {
                    registerUser(_gmail.value, _password.value)
                    _event.emit(RegistrationUiEvents.NavigateToHomeScreen)
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

            RegistrationEvents.NavigateToLogInUser -> {
                viewModelScope.launch {
                    _event.emit(RegistrationUiEvents.NavigateToLogInScreen)
                }
            }

            is RegistrationEvents.UpdateUserImage -> {
                _selectedImage.value = events.image
             /*   events.image?.let { uri ->
                    val bitmap = uriToBitmap(uri, context = events.context)
                    val encodedImage = bitmapToBase64(bitmap)
                    _bitmapImage.value = encodedImage
                }*/
            }
        }
    }

}

@RequiresApi(Build.VERSION_CODES.P)
private fun uriToBitmap(uri: Uri, context: Context): Bitmap {
    val source = ImageDecoder.createSource(context.contentResolver, uri)
    return ImageDecoder.decodeBitmap(source)
}

private fun bitmapToBase64(bitmap: Bitmap): String {
    val byteArrayOutputStream = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
    val byteArray = byteArrayOutputStream.toByteArray()
    return Base64.encodeToString(byteArray, Base64.DEFAULT)
}
