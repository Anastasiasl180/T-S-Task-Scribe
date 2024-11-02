package com.aopr.notes_presentation.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.aopr.notes_presentation.view_model.AllNotesViewModel
import com.aopr.notes_presentation.view_model.events.AllNotesEvent
import com.aopr.notes_presentation.view_model.uiEventHandler.UiEvenHandler
import org.koin.androidx.compose.koinViewModel

@Composable
fun AllNotesScreen() {
    val viewModel = koinViewModel<AllNotesViewModel>()
    val listOfNotes = viewModel.listOfNotes.value?.toList()
    BackHandler {

    }
    UiEvenHandler()
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(20.dp),
            horizontalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            if (listOfNotes != null) {
                items(listOfNotes) { note ->
                    NoteCard(
                        tittle = note.tittle,
                        description = note.description,
                        goToNote = { viewModel.onEvent(AllNotesEvent.NavigateToCreateNoteScreen) })

                }
            }

        }
    }

}

@Composable
fun NoteCard(tittle: String, description: String, goToNote: () -> Unit) {

    ElevatedCard(
        modifier = Modifier.height(200.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Blue),
        shape = MaterialTheme.shapes.extraLarge
    ) {
        Box(modifier = Modifier.fillMaxSize(0.6f), contentAlignment = Alignment.Center) {
            Column(modifier = Modifier.fillMaxSize()) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.3f)
                ) {
                    Text(text = tittle)
                }
                Row(
                    modifier = Modifier
                        .fillMaxHeight(0.4f)
                        .fillMaxWidth()
                ) {
                    Text(text = description)
                }
                Row(modifier = Modifier.fillMaxSize()) {
                    Icon(
                        imageVector = Icons.Default.ArrowForward,
                        contentDescription = "",
                        modifier = Modifier.clickable { goToNote() }
                    )
                }
            }
        }

    }

}