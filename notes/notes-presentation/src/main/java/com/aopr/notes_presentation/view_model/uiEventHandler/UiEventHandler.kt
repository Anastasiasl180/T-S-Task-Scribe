package com.aopr.notes_presentation.view_model.uiEventHandler

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import com.aopr.notes_presentation.navigation.AllNotesNavRoutes
import com.aopr.notes_presentation.view_model.AllNotesViewModel
import com.aopr.shared_ui.util.LocalNavigator
import com.aopr.shared_ui.util.currentOrThrow
import org.koin.androidx.compose.koinViewModel

@Composable
fun UiEvenHandler() {
    val navigator = LocalNavigator.currentOrThrow()
    val viewModel = koinViewModel<AllNotesViewModel>()
    LaunchedEffect(Unit) {
        viewModel.event.collect(){uiEvents->
            when(uiEvents){
                AllNotesViewModel.UiEvent.NavigateToCreateNoteScreen -> {
                    navigator.navigate(AllNotesNavRoutes.CreatingNoteScreen)
                }
            }
        }

    }
}