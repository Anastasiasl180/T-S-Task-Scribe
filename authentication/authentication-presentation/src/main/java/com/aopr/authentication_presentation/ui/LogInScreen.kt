package com.aopr.authentication_presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
    val isShoredDialog by viewModel.isDialogShowed
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
                Row(modifier = Modifier.fillMaxWidth()) {
                    TextButton(onClick = {
                        viewModel.onEvent(LogInEvents.ShowAlertDialog)
                    }) {
                        Text(text = "Forget password?")
                    }
                }

                Button(onClick = { viewModel.onEvent(LogInEvents.LogInUser) }) {
                    Text(text = "Save")
                }

            }

        }
        if (isShoredDialog) {
            SendRestoreCodeAlertDialog(
                onGmailChange = {
                    viewModel.onEvent(LogInEvents.UpdateGmailForResettingPassword(it))
                },
                hideDialog = { viewModel.onEvent(LogInEvents.HideAlertDialog) },
                sendCode = { viewModel.onEvent(LogInEvents.SendResetPasswordCode(it)) })
        }
    }
}

@Composable
fun SendRestoreCodeAlertDialog(
    onGmailChange: (String) -> Unit,
    hideDialog: () -> Unit,
    sendCode: (String) -> Unit,

) {

    var gmail by remember {
        mutableStateOf("")
    }



    AlertDialog(
        text = {
            TextField(value = gmail, onValueChange = {  gmail = it
                onGmailChange(it) })
        },
        onDismissRequest = hideDialog,
        confirmButton = {
            Row {
                Button(onClick = {
                    sendCode(gmail)
                    hideDialog()
                }) {

                }


            }

        },


        )


}