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
import com.aopr.authentication_presentation.events.log_in_events.LogInEvents
import com.aopr.authentication_presentation.handler.LogInUiEventHandler
import com.aopr.authentication_presentation.model.LogInViewModel
import org.koin.androidx.compose.koinViewModel


@Composable
fun LogInScreen() {
   LogInUiEventHandler()
    val viewModel = koinViewModel<LogInViewModel>()
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
                    viewModel.onEvent(LogInEvents.UpdateGmail(it))
                })
                TextField(value = password, onValueChange = {
                    viewModel.onEvent(LogInEvents.UpdatePassword(it))
                })
                Button(onClick = { viewModel.onEvent(LogInEvents.LogInUser) }) {
                    Text(text = "Save")
                }

            }

        }

    }
}