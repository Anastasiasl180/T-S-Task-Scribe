package com.aopr.notes_presentation.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.aopr.notes_presentation.view_model.CreatingNoteViewModel
import com.aopr.notes_presentation.view_model.events.CreatingNoteEvent
import com.aopr.notes_presentation.view_model.uiEventHandler.UiHandlerForCreatingNote
import org.koin.androidx.compose.koinViewModel

@Composable
fun CreatingNoteScreen() {
    val viewModel = koinViewModel<CreatingNoteViewModel>()
    val tittle by viewModel.tittleOfNote.collectAsState()
    val description by viewModel.descriptionOfNote.collectAsState()
    UiHandlerForCreatingNote()
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(modifier = Modifier.fillMaxSize()) {


            TextField(value = tittle, onValueChange = { tittle ->
                viewModel.onEvent(CreatingNoteEvent.UpdateTittle(tittle))
            })
            TextField(value = description, onValueChange = { description ->
                viewModel.onEvent(CreatingNoteEvent.UpdateDescription(description))
            })
            TextButton(onClick = { viewModel.onEvent(CreatingNoteEvent.SaveNote) }) {
Text(text = "ffdf")
            }
        }
    }
}