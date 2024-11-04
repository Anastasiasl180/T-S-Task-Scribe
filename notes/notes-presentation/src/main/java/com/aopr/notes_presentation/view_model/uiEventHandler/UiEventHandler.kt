package com.aopr.notes_presentation.view_model.uiEventHandler

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.toRoute
import com.aopr.notes_presentation.navigation.AllNotesRoutes
import com.aopr.notes_presentation.view_model.AllNotesViewModel
import com.aopr.notes_presentation.view_model.CreatingNoteViewModel
import com.aopr.notes_presentation.view_model.events.CreatingNoteEvent
import com.aopr.shared_ui.util.LocalNavigator
import com.aopr.shared_ui.util.currentOrThrow
import org.koin.androidx.compose.koinViewModel

@Composable
fun UiEvenHandler() {
    val navigator = LocalNavigator.currentOrThrow()
    val viewModel = koinViewModel<AllNotesViewModel>()
    LaunchedEffect(Unit) {
        viewModel.event.collect { uiEvents ->
            when (uiEvents) {
                is AllNotesViewModel.UiEvent.NavigateToCreateNoteScreen -> {
                    navigator.navigate(AllNotesRoutes.CreatingNoteScreen(uiEvents.id))
                }
            }
        }

    }
}

@Composable
fun UiHandlerForCreatingNote() {
    val navigator = LocalNavigator.currentOrThrow()
    val id = navigator.currentBackStackEntry?.toRoute<AllNotesRoutes.CreatingNoteScreen>()
    val idd = id?.id
    val viewModel = koinViewModel<CreatingNoteViewModel>()
    LaunchedEffect(Unit) {
        if (idd != null) {
            viewModel.onEvent(CreatingNoteEvent.GetNoteById(idd))
        }
    }
}