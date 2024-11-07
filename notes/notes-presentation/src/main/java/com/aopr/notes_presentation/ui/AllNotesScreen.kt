package com.aopr.notes_presentation.ui

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.aopr.notes_presentation.R
import com.aopr.notes_presentation.view_model.AllNotesViewModel
import com.aopr.notes_presentation.view_model.events.allNotesEvents.AllNotesEvent
import com.aopr.notes_presentation.view_model.uiEventHandler.UiEvenHandler
import com.radusalagean.infobarcompose.InfoBar
import com.radusalagean.infobarcompose.InfoBarMessage
import org.koin.androidx.compose.koinViewModel

@Composable
fun AllNotesScreen() {
    val viewModel = koinViewModel<AllNotesViewModel>()
    val listOfNotes = viewModel.listOfNotes.value?.toList()
    BackHandler {

    }
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {
                viewModel.onEvent(
                    AllNotesEvent.NavigateToCreateNoteScreen(
                        null
                    )
                )
            }, modifier = Modifier.clip(shape = MaterialTheme.shapes.extraLarge)) {
                Text(text = stringResource(id = R.string.addNoteFloatButton))
            }
        }

    ) { _ ->

        UiEvenHandler()
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {

            Box(
                modifier = Modifier
                    .fillMaxHeight(0.9f)
                    .fillMaxWidth(0.9f)
            ) {
                Column(modifier = Modifier.fillMaxSize()) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(0.08f), verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = stringResource(id = com.aopr.notes_domain.R.string.Notes))
                    }
                    LazyVerticalGrid(
                        modifier = Modifier.clip(MaterialTheme.shapes.large),
                        columns = GridCells.Fixed(2),
                        verticalArrangement = Arrangement.spacedBy(20.dp),
                        horizontalArrangement = Arrangement.spacedBy(20.dp)
                    ) {
                        if (listOfNotes != null) {
                            items(listOfNotes) { note ->
                                NoteCard(
                                    tittle = note.tittle,
                                    description = note.description,
                                    deleteNote = { viewModel.onEvent(AllNotesEvent.DeleteNote(note)) },
                                    goToNote = {
                                        viewModel.onEvent(
                                            AllNotesEvent.NavigateToCreateNoteScreen(
                                                note.id
                                            )
                                        )
                                    }, time = note.date,
                                    pinNote = {
                                        viewModel.onEvent(AllNotesEvent.PinNote(note))
                                    }, isPinned = note.isPinned, showInfoBarMessage = {
                                        viewModel.onEvent(AllNotesEvent.TapNoteCardMessage)
                                    }, noteIndex = note.id
                                )

                            }
                        }

                    }
                }
            }
            InfoBar(offeredMessage = viewModel.infoBar.value) {

            }
        }
    }


}

@Composable
fun NoteCard(
    tittle: String,
    description: String,
    time: String,
    isPinned: Boolean,
    goToNote: () -> Unit,
    deleteNote: () -> Unit,
    pinNote: () -> Unit,
    showInfoBarMessage: () -> Unit, noteIndex: Int
) {

    val isPressed = remember { mutableStateOf(false) }
    val animation = animateFloatAsState(
        targetValue = if (isPressed.value) 0.9f else 1f,
        animationSpec = spring(Spring.DampingRatioLowBouncy),
        label = ""
    )

    ElevatedCard(
        modifier = Modifier
            .height(220.dp)
            .scale(animation.value)
            .pointerInput(noteIndex) {
                this.detectTapGestures(onLongPress = {
                    pinNote()
                }, onPress = {
                }) {
                    showInfoBarMessage()
                }
            },
        colors = CardDefaults.cardColors(containerColor = Color.Blue),
        shape = MaterialTheme.shapes.extraLarge,
    ) {

        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {

            Column(
                modifier = Modifier
                    .fillMaxHeight(0.9f)
                    .fillMaxWidth(0.9f)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.3f),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = tittle)
                }
                Row(
                    modifier = Modifier
                        .fillMaxHeight(0.6f)
                        .fillMaxWidth()
                ) {
                    Text(
                        text = if (description.length > 50) description.take(50) + "..." else description,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxHeight(0.6f)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = "",
                        modifier = Modifier.clickable { goToNote() }
                    )
                    if (isPinned) {
                        Icon(
                            painter = painterResource(id = R.drawable.pin),
                            contentDescription = ""
                        )
                    }
                    IconButton(onClick = { deleteNote() }) {
                        Icon(imageVector = Icons.Default.Delete, contentDescription = "")
                    }
                }
                Row(modifier = Modifier.fillMaxSize()) {
                    Text(text = time)
                }

            }
        }

    }
}
