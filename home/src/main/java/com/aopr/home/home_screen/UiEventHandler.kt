package com.aopr.home.home_screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.aopr.home.home_screen.navigation.HomeNavRoutes
import com.aopr.notes_presentation.view_model.NotesViewModel
import com.aopr.notes_presentation.view_model.events.notesEvents.NotesUiEvents
import com.aopr.shared_ui.util.LocalNavigator
import com.aopr.shared_ui.util.currentOrThrow
import org.koin.androidx.compose.koinViewModel


@Composable
fun HomeUiEventHandler() {
    val navigator = LocalNavigator.currentOrThrow()
    val viewModel = koinViewModel<NotesViewModel>()
    LaunchedEffect(Unit) {
        viewModel.uiEvents.collect { uiEvents ->
            when (uiEvents) {
                NotesUiEvents.NavigateToAllNotesScreen -> {
                    navigator.navigate(HomeNavRoutes.AllNotesScreen)

                }
                NotesUiEvents.NavigateToAllTasks -> {
                    navigator.navigate(HomeNavRoutes.AllTasksScreen)
                }
            }

        }
    }

}