package com.aopr.notes_presentation.view_model.ui_event_handlers

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.toRoute
import com.aopr.notes_presentation.navigation.AllNotesRoutes
import com.aopr.notes_presentation.view_model.AllNotesViewModel
import com.aopr.notes_presentation.view_model.CreatingNoteViewModel
import com.aopr.notes_presentation.view_model.events.all_notes_events.AllNotesUiEvent
import com.aopr.notes_presentation.view_model.events.creating_note_events.CreatingNoteEvents
import com.aopr.notes_presentation.view_model.events.creating_note_events.CreatingNoteUiEvents
import com.aopr.shared_ui.util.LocalNavigator
import com.aopr.shared_ui.util.currentOrThrow
import org.koin.androidx.compose.koinViewModel

@Composable
fun UiEventHandlerForAllNotesScreen() {
    val navigator = LocalNavigator.currentOrThrow()
    val viewModel = koinViewModel<AllNotesViewModel>()
    LaunchedEffect(Unit) {
        viewModel.event.collect { uiEvents ->
            when (uiEvents) {
                is AllNotesUiEvent.NavigateToCreateNoteScreen -> {
                    navigator.navigate(AllNotesRoutes.CreatingNoteScreen(uiEvents.id))

                }
                AllNotesUiEvent.NavigateBack -> {
                    navigator.popBackStack()
                }
            }
        }

    }
}

@Composable
fun UiEventHandlerForCreatingNoteScreen() {
    val navigator = LocalNavigator.currentOrThrow()
    val route = navigator.currentBackStackEntry?.toRoute<AllNotesRoutes.CreatingNoteScreen>()
    val id = route?.id
    val viewModel = koinViewModel<CreatingNoteViewModel>()
    LaunchedEffect(Unit) {
        viewModel.event.collect { event ->
            when (event) {
                CreatingNoteUiEvents.NavigateToAllNotes -> {
                    navigator.popBackStack()
                }
            }
        }
    }
    LaunchedEffect(Unit) {
        if (id != null) {
            viewModel.onEvent(CreatingNoteEvents.GetNoteById(id))
        }
    }
}