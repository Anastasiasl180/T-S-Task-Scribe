package com.aopr.authentication_presentation.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.aopr.authentication_presentation.R
import com.aopr.authentication_presentation.view_models.events.log_in_events.LogInEvents
import com.aopr.authentication_presentation.view_models.ui_events_handler.LogInUiEventHandler
import com.aopr.authentication_presentation.view_models.LogInViewModel
import com.aopr.authentication_presentation.ui.ui_elements.CustomTextField
import com.aopr.shared_ui.cardsView.background
import com.aopr.shared_ui.cardsView.gradientOfDialogs
import org.koin.androidx.compose.koinViewModel


@Composable
fun LogInScreen() {
    LogInUiEventHandler()
    val viewModel = koinViewModel<LogInViewModel>()
    val gmail by viewModel.gmail.collectAsState()
    val backgroundTheme = background()
    val isShoredDialog by viewModel.isDialogShowed
    val password by viewModel.password.collectAsState()
    Scaffold { _ ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(backgroundTheme), contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .fillMaxHeight(0.7f)
                    .fillMaxWidth(0.8f),
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CustomTextField(
                    gmail, onValueChange = { viewModel.onEvent(LogInEvents.UpdateGmail(it)) },
                    stringResource(R.string.gmail)
                )
                CustomTextField(
                    password,
                    onValueChange = { viewModel.onEvent(LogInEvents.UpdatePassword(it)) },
                    placeholder = stringResource(R.string.password)
                )

                Row(modifier = Modifier.fillMaxWidth()) {
                    TextButton(onClick = {
                        viewModel.onEvent(LogInEvents.ShowAlertDialog)
                    }) {
                        Text(
                            text = stringResource(R.string.forget_password), color = Color.White,
                            fontFamily = FontFamily(
                                Font(com.aopr.shared_ui.R.font.open_sans_light)
                            )
                        )
                    }
                }
                Row(
                    modifier = Modifier
                        .fillMaxHeight(0.2f)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(
                        onClick = { viewModel.onEvent(LogInEvents.LogInUser) },
                        modifier = Modifier
                            .fillMaxHeight(0.75f)
                            .fillMaxWidth(0.8f),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                        border = BorderStroke(width = 1.dp, color = Color.White)
                    ) {
                        Text(
                            text = stringResource(R.string.login), color = Color.White,
                            fontFamily = FontFamily(
                                Font(com.aopr.shared_ui.R.font.open_sans_light)
                            )
                        )
                    }
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

    val gradient = gradientOfDialogs()
    Dialog(onDismissRequest = hideDialog) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(375.dp)
                .padding(16.dp)
                .clip(shape = RoundedCornerShape(30.dp))
        ) {
            ElevatedCard(
                modifier = Modifier
                    .fillMaxSize()
                    .border(
                        brush = gradient, width = 3.dp, shape = RoundedCornerShape(30.dp)
                    ),
                shape = MaterialTheme.shapes.extraSmall,
                colors = CardDefaults.cardColors(
                    containerColor = colorResource(com.aopr.shared_ui.R.color.backgroundOfDialogs)
                )
            ) {

                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.SpaceEvenly,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {

                    Text(
                        text = stringResource(
                            R.string.restoring_account
                        ),
                        modifier = Modifier.padding(16.dp),
                        fontFamily = FontFamily(Font(com.aopr.shared_ui.R.font.open_sans_light))

                    )

                    OutlinedTextField(
                        shape = MaterialTheme.shapes.medium,
                        modifier = Modifier
                            .fillMaxWidth(0.9f)
                            .height(60.dp),
                        placeholder = {
                            Text(
                                text = stringResource(R.string.gmail),
                                fontFamily = FontFamily(Font(com.aopr.shared_ui.R.font.open_sans_light))
                            )
                        },
                        value = gmail,
                        onValueChange = {   gmail = it
                            onGmailChange(it) },
                        colors = TextFieldDefaults.colors(
                            focusedIndicatorColor = Color.Black,
                            unfocusedIndicatorColor = Color.Black,
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,


                            ),

                        )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                    ) {
                        TextButton(
                            onClick = hideDialog,
                            modifier = Modifier.padding(8.dp),
                        ) {
                            Text(
                                stringResource(com.aopr.shared_ui.R.string.dismiss),
                                color = Color.White,
                                fontFamily = FontFamily(Font(com.aopr.shared_ui.R.font.open_sans_light))
                            )
                        }
                        TextButton(
                            onClick = {sendCode(gmail)} ,
                            modifier = Modifier.padding(8.dp),
                        ) {
                            Text(
                                stringResource(com.aopr.shared_ui.R.string.confirm),
                                color = Color.White,
                                fontFamily = FontFamily(Font(com.aopr.shared_ui.R.font.open_sans_light))
                            )
                        }
                    }

                }
            }
        }
    }


}