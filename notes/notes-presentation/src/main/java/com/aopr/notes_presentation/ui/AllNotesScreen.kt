package com.aopr.notes_presentation.ui

import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aopr.notes_domain.models.Note
import com.aopr.notes_presentation.view_model.AllNotesViewModel
import com.aopr.notes_presentation.view_model.events.all_notes_events.AllNotesEvent
import com.aopr.notes_presentation.view_model.ui_event_handlers.UiEventHandlerForAllNotesScreen
import com.aopr.shared_ui.cardsView.background
import com.aopr.shared_ui.cardsView.cardViews
import com.aopr.shared_ui.util.ViewModelKit
import kotlinx.coroutines.delay
import org.koin.android.annotation.KoinViewModel
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AllNotesScreen() {
    val viewModel = koinViewModel<AllNotesViewModel>()
    val listOfNotes = viewModel.listOfNotes.value?.toList()
    val isInSelectionMode by viewModel.isInSelectedMode.collectAsState()
    val backgroundTheme = background()
    BackHandler {

    }
    Scaffold(

        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    viewModel.onEvent(
                        AllNotesEvent.NavigateToCreateNoteScreen(
                            null
                        )
                    )
                }, modifier = Modifier
                    .clip(CircleShape)
                    .size(60.dp), containerColor = Color.DarkGray.copy(alpha = 0.6f)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "AddFloatingActionButton",
                    modifier = Modifier.size(20.dp)
                )


            }
        }, topBar = {
            TopAppBar(colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent),
                navigationIcon = {
                    IconButton(
                        onClick = { viewModel.onEvent(AllNotesEvent.NavigateBack) },
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
                },
                title = { /*TODO*/ })
        }

    ) { _ ->


        UiEventHandlerForAllNotesScreen()
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(backgroundTheme), contentAlignment = Alignment.Center
        ) {

            Box(
                modifier = Modifier
                    .fillMaxHeight(0.9f)
                    .fillMaxWidth(0.9f)
            ) {
                Column(modifier = Modifier.fillMaxSize()) {
                    Spacer(modifier = Modifier.height(70.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(0.08f),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = stringResource(id = com.aopr.notes_domain.R.string.Notes),
                            fontSize = 20.sp
                        )
                        TextButton(onClick = {
                            viewModel.onEvent(AllNotesEvent.TurnOnSelectionModeForDelete)
                        }) {
                            Text(
                                text = stringResource(id = com.aopr.shared_ui.R.string.chooseToDelete),
                                color = Color.White
                            )
                        }
                        if (isInSelectionMode) {
                            Row {
                                TextButton(onClick = { viewModel.cancelNoteFromDelete() }) {
                                    Text(
                                        text = stringResource(id = com.aopr.shared_ui.R.string.cancel),
                                        color = Color.White
                                    )
                                }

                                TextButton(onClick = { viewModel.deleteSelectedNotes() }) {
                                    Text(
                                        text = stringResource(id = com.aopr.shared_ui.R.string.delete),
                                        color = Color.White
                                    )
                                }
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(10.dp))
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

                                    goToNote = {
                                        viewModel.onEvent(
                                            AllNotesEvent.NavigateToCreateNoteScreen(
                                                note.id
                                            )
                                        )
                                    },
                                    time = note.date,
                                    pinNote = {
                                        viewModel.onEvent(AllNotesEvent.PinNote(note))
                                    },
                                    isPinned = note.isPinned,
                                    showInfoBarMessage = {
                                        viewModel.onEvent(AllNotesEvent.TapNoteCardMessage)
                                    },
                                    note = note,
                                    isInSelectionMode = isInSelectionMode,
                                    onSelectNote = { selectedNote, isSelected ->
                                        if (isSelected) {
                                            viewModel.addNoteToDelete(selectedNote)
                                        } else {
                                            viewModel.removeNoteFromDelete(selectedNote)
                                        }

                                    }
                                )

                            }
                        }

                    }
                }
            }
            viewModel.infoBar.value?.let { com.aopr.shared_ui.infoBar.CustomInfoBar(message = it) }


        }
    }


}

@Composable
fun NoteCard(
    goToNote: () -> Unit,
    pinNote: () -> Unit,
    showInfoBarMessage: () -> Unit,
    onSelectNote: (Note, Boolean) -> Unit,
    time: String,
    tittle: String,
    description: String,
    isPinned: Boolean,
    isInSelectionMode: Boolean,
    note: Note
) {
    val isPressed = remember(note) { mutableStateOf(false) }
    val animation = animateFloatAsState(
        targetValue = if (isPressed.value) 0.99f else 1f,
        animationSpec = spring(Spring.StiffnessLow),
        label = ""
    )
    val isSelected = remember(note) { mutableStateOf(false) }

    ElevatedCard(
        modifier = Modifier
            .height(220.dp)
            .scale(animation.value)
            .pointerInput(note) {
                this.detectTapGestures(onLongPress = {
                    pinNote()
                }, onPress = {
                    delay(20)
                    isPressed.value = true
                    this.awaitRelease()
                    isPressed.value = false
                }) {
                    showInfoBarMessage()
                }
            },
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        shape = MaterialTheme.shapes.extraLarge,
    ) {

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(cardViews()), contentAlignment = Alignment.Center
        ) {

            Column(
                modifier = Modifier
                    .fillMaxHeight(0.9f)
                    .fillMaxWidth(0.9f)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.2f),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.End
                ) {

                    IconButton(
                        onClick = { goToNote() }, modifier = Modifier.size(40.dp),
                        colors = IconButtonDefaults.outlinedIconButtonColors(
                            Color.White.copy(
                                alpha = 0.2f
                            )
                        ),

                        ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Default.KeyboardArrowRight,
                            contentDescription = "ArrowRight",
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
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
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (isPinned) {
                        Row(
                            modifier = Modifier
                                .fillMaxHeight()
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = stringResource(id = com.aopr.shared_ui.R.string.pinned))
                            if (isInSelectionMode) {
                                Checkbox(checked = isSelected.value, onCheckedChange = { checked ->
                                    isSelected.value = checked
                                    onSelectNote(note, checked)
                                })
                            }
                        }

                    } else {
                        if (isInSelectionMode) {
                            Checkbox(checked = isSelected.value, onCheckedChange = { checked ->
                                isSelected.value = checked
                                onSelectNote(note, checked)
                            })
                        }
                    }


                }
                Row(modifier = Modifier.fillMaxSize()) {
                    Text(text = time, fontSize = 10.sp)
                }

            }
        }

    }
}