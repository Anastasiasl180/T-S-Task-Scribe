package com.aopr.authentication_presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.aopr.authentication_presentation.events.registration_events.RegistrationEvents
import com.aopr.authentication_presentation.handler.RegistrationUiEventHandler
import com.aopr.authentication_presentation.model.RegistrationViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun RegistrationScreen() {
    RegistrationUiEventHandler()
    val viewModel = koinViewModel<RegistrationViewModel>()
    val userName by viewModel.userName.collectAsState()
    val gmail by viewModel.gmail.collectAsState()
    val password by viewModel.password.collectAsState()
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
                TextField(value = password, onValueChange ={
                    viewModel.onEvent(RegistrationEvents.UpdatePassword(it))
                } )
                TextField(value = userName, onValueChange ={
                    viewModel.onEvent(RegistrationEvents.UpdateUserName(it))
                } )
                Button(onClick = { viewModel.onEvent(RegistrationEvents.RegisterUser) }) {
                    Text(text = "Save")
                }
                Button(onClick = { viewModel.onEvent(RegistrationEvents.NavigateToLogInUser) }) {
                    Text(text = "logIn")
                }

            }

        }

    }
}