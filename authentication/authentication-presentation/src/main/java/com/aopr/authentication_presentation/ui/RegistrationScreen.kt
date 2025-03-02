package com.aopr.authentication_presentation.ui

import android.net.Uri
import android.os.Build
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.aopr.authentication_presentation.R
import com.aopr.authentication_presentation.view_models.events.registration_events.RegistrationEvents
import com.aopr.authentication_presentation.view_models.ui_events_handler.RegistrationUiEventHandler
import com.aopr.authentication_presentation.view_models.RegistrationViewModel
import com.aopr.authentication_presentation.ui.ui_elements.CustomTextField
import com.aopr.shared_ui.cardsView.background
import com.canhub.cropper.CropImageContract
import com.canhub.cropper.CropImageContractOptions
import com.canhub.cropper.CropImageOptions
import org.koin.androidx.compose.koinViewModel

@RequiresApi(Build.VERSION_CODES.P)
@Composable
fun RegistrationScreen() {
    RegistrationUiEventHandler()
    val background = background()
    val viewModel = koinViewModel<RegistrationViewModel>()
    val userName by viewModel.userName.collectAsState()
    val gmail by viewModel.gmail.collectAsState()
    val userImage by viewModel.selectedImage.collectAsState()
    val password by viewModel.password.collectAsState()
    val context = LocalContext.current

    var imageUri: Uri? by remember { mutableStateOf(null) }

    val imageCropLauncher =
        rememberLauncherForActivityResult(contract = CropImageContract()) { result ->
            if (result.isSuccessful) {
                result.uriContent?.let {
                    imageUri = it
                    viewModel.onEvent(RegistrationEvents.UpdateUserImage(it, context))
                }
            } else {
                Toast.makeText(context, "Image cropping failed", Toast.LENGTH_SHORT).show()
                println("ImageCropping error: ${result.error}")
            }
        }


    val pickImageLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri ->
        uri?.let {
            val cropOptions = CropImageContractOptions(it, CropImageOptions())
            imageCropLauncher.launch(cropOptions)
        }
    }

    Scaffold { _ ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(background), contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .fillMaxHeight(0.95f)
                    .fillMaxWidth(0.8f),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.1f), verticalAlignment = Alignment.Bottom
                ) {
                    Text(
                        text = stringResource(R.string.Registration), fontFamily = FontFamily(
                            Font(com.aopr.shared_ui.R.font.open_sans_light),
                        ), color = Color.White, fontSize = 30.sp
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.1f),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    TextButton(onClick = {
                        pickImageLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))

                    }) {
                        Text(
                            text = stringResource(R.string.pick_photo),
                            color = Color.White,
                            fontFamily = FontFamily(
                                Font(com.aopr.shared_ui.R.font.open_sans_light)
                            )
                        )
                    }
                    Box(modifier = Modifier
                        .size(80.dp)
                        .border(1.dp, Color.White, CircleShape)
                    ) {
                        imageUri?.let {
                            AsyncImage(
                                model = userImage,
                                contentDescription = "",
                                modifier = Modifier
                                    .size(80.dp)
                                    .clip(CircleShape),
                                contentScale = ContentScale.Crop
                            )
                        }
                    }
                }



                CustomTextField(
                    gmail,
                    onValueChange = { viewModel.onEvent(RegistrationEvents.UpdateGmail(it)) },
                    placeholder = stringResource(id = R.string.gmail)
                )
                CustomTextField(
                    password,
                    onValueChange = { viewModel.onEvent(RegistrationEvents.UpdatePassword(it)) },
                    placeholder = stringResource(id = R.string.password)
                )
                CustomTextField(
                    userName,
                    onValueChange = { viewModel.onEvent(RegistrationEvents.UpdateUserName(it)) },
                    placeholder = stringResource(id = R.string.username)
                )

                Row(
                    modifier = Modifier
                        .fillMaxHeight(0.1f)
                        .fillMaxWidth(0.8f),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Text(
                        text = stringResource(R.string.already_have_an_acc),
                        fontFamily = FontFamily(
                            Font(com.aopr.shared_ui.R.font.open_sans_light)
                        )
                    )
                    TextButton(onClick = { viewModel.onEvent(RegistrationEvents.NavigateToLogInUser) }) {
                        Text(
                            text = stringResource(R.string.login),
                            color = Color.White,
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
                        onClick = { viewModel.onEvent(RegistrationEvents.RegisterUser) },
                        modifier = Modifier
                            .fillMaxHeight(0.75f)
                            .fillMaxWidth(0.8f),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                        border = BorderStroke(width = 1.dp, color = Color.White)
                    ) {
                        Text(text = stringResource(R.string.register), color = Color.White)
                    }
                }


            }

        }

    }
}

/*  OutlinedTextField(
                  shape = MaterialTheme.shapes.medium,
                  modifier = Modifier
                      .fillMaxWidth()
                      .height(60.dp
                      .border(
                          width = 0.5.dp,
                          color = Color.Transparent,
                          shape = MaterialTheme.shapes.medium
                      ),
                  placeholder = {
                      Text(text = stringResource(id = com.aopr.shared_ui.R.string.tittle))
                  },
                  value = gmail,
                  onValueChange = {
                      viewModel.onEvent(RegistrationEvents.UpdateGmail(it))

                  },
                  colors = OutlinedTextFieldDefaults.colors(
                      focusedContainerColor = Color.LightGray.copy(alpha = 0.2f),
                      unfocusedContainerColor = Color.LightGray.copy(alpha = 0.2f),
                      focusedBorderColor = Color.White.copy(alpha = 0.5f),
                      unfocusedBorderColor = Color.Transparent,cursorColor = Color.White
                  ),

                  )
            */