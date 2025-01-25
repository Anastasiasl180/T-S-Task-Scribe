package com.aopr.notes_presentation.ui

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aopr.notes_presentation.view_model.CreatingNoteViewModel
import com.aopr.notes_presentation.view_model.events.creating_note_events.CreatingNoteEvents
import com.aopr.notes_presentation.view_model.ui_event_handlers.UiEventHandlerForCreatingNoteScreen
import com.aopr.shared_ui.cardsView.background
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreatingNoteScreen() {
    val viewModel = koinViewModel<CreatingNoteViewModel>()
    val tittle by viewModel.tittleOfNote.collectAsState()
    val backgroundTheme = background()
    val description by viewModel.descriptionOfNote.collectAsState()

    UiEventHandlerForCreatingNoteScreen()
    Scaffold(
        topBar = {
            TopAppBar(colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent),
                navigationIcon = {
                    IconButton(
                        onClick = { viewModel.onEvent(CreatingNoteEvents.NavigateToAllNotes) },
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(Color.DarkGray.copy(alpha = 0.6f))
                            .size(50.dp)
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Default.KeyboardArrowLeft,
                            contentDescription = "",
                            tint = Color.White, modifier = Modifier.size(20.dp)
                        )
                    }
                }, actions = {

                    IconButton(
                        onClick = { viewModel.onEvent(CreatingNoteEvents.PinNote) },
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(
                                Color.DarkGray.copy(
                                    alpha = 0.6f
                                )
                            )
                            .size(50.dp)
                    ) {
                        Text(
                            text = if (viewModel.isNotePinned.value) stringResource(id = com.aopr.shared_ui.R.string.pinned)
                            else stringResource(id = com.aopr.shared_ui.R.string.unPinned),
                            fontSize = 10.sp
                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    IconButton(
                        onClick = { viewModel.onEvent(CreatingNoteEvents.SaveNote) },
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(Color.DarkGray.copy(alpha = 0.6f))
                            .size(50.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "AddIconButton",
                            modifier = Modifier.size(20.dp)
                        )

                    }

                },
                title = { /*TODO*/ })
        }
    ) { paddingValues ->

        Box(
            modifier = Modifier
                .background(backgroundTheme), contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Spacer(modifier = Modifier.height(100.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.15f),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextField(
                        modifier = Modifier
                            .fillMaxSize(),
                        placeholder = {
                            Text(
                                text = stringResource(id = com.aopr.shared_ui.R.string.tittle),
                                fontSize = 35.sp,
                                color = Color.White
                            )
                        },
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            disabledContainerColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent
                        ),
                        value = tittle,
                        onValueChange = { tittle ->
                            viewModel.onEvent(CreatingNoteEvents.UpdateTittle(tittle))
                        },
                        textStyle = TextStyle(
                            fontSize = 35.sp
                        ),
                    )
                }
                TextField(
                    modifier = Modifier.fillMaxSize(),
                    value = description,
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        disabledContainerColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent
                    ),
                    placeholder = {
                        Text(
                            text = stringResource(
                                id = com.aopr.shared_ui.R.string.description
                            ),
                            color = Color.White, fontSize = 20.sp
                        )
                    },
                    onValueChange = { description ->
                        viewModel.onEvent(CreatingNoteEvents.UpdateDescription(description))
                    },
                    textStyle = TextStyle(
                        fontSize = 20.sp
                    )
                )

            }
            viewModel.infoBar.value?.let { com.aopr.shared_ui.infoBar.CustomInfoBar(message = it) }

        }


    }

}