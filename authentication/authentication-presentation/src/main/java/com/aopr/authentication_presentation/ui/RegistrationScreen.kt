package com.aopr.authentication_presentation.ui

import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil3.compose.AsyncImage
import com.aopr.authentication_presentation.events.registration_events.RegistrationEvents
import com.aopr.authentication_presentation.handler.RegistrationUiEventHandler
import com.aopr.authentication_presentation.model.RegistrationViewModel
import org.koin.androidx.compose.koinViewModel

@RequiresApi(Build.VERSION_CODES.P)
@Composable
fun RegistrationScreen() {
    RegistrationUiEventHandler()
    val viewModel = koinViewModel<RegistrationViewModel>()
    val userName by viewModel.userName.collectAsState()
    val gmail by viewModel.gmail.collectAsState()
    val userImage by viewModel.selectedImage.collectAsState()
    val password by viewModel.password.collectAsState()
    val context = LocalContext.current
    val singlePhotoPickerLauncher = rememberLauncherForActivityResult(contract = ActivityResultContracts.PickVisualMedia(), onResult = {uri->
       viewModel.onEvent(RegistrationEvents.UpdateUserImage(uri,context))
    })
    Scaffold { _ ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Blue)
        ) {
            Column(
                modifier = Modifier.fillMaxSize(0.7f),
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                TextField(value = gmail, onValueChange = {
                    viewModel.onEvent(RegistrationEvents.UpdateGmail(it))
                })
                TextField(value = password, onValueChange = {
                    viewModel.onEvent(RegistrationEvents.UpdatePassword(it))
                })
                TextField(value = userName, onValueChange = {
                    viewModel.onEvent(RegistrationEvents.UpdateUserName(it))
                })
                Button(onClick = { viewModel.onEvent(RegistrationEvents.RegisterUser) }) {
                    Text(text = "Save")
                }
                Button(onClick = {

                    singlePhotoPickerLauncher.launch(
                        PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                    )

                }) {
                    Text(text = "Pick photo")
                }
                AsyncImage(
                    model = userImage,
                    contentDescription = "",
                    modifier = Modifier.fillMaxWidth(),
                    contentScale = ContentScale.Crop
                )
                Button(onClick = { viewModel.onEvent(RegistrationEvents.NavigateToLogInUser) }) {
                  Text(text = "logIn")
                }

            }

        }

    }
}