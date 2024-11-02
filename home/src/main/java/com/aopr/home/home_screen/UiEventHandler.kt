package com.aopr.home.home_screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.aopr.notes_presentation.view_model.NotesViewModel
import com.aopr.home.home_screen.navigation.HomeNavRoutes
import com.aopr.home.home_screen.viewModel.HomeViewModel
import com.aopr.shared_ui.util.LocalNavigator
import com.aopr.shared_ui.util.currentOrThrow
import org.koin.androidx.compose.koinViewModel


@Composable
fun HomeUiEventHandler() {
    val navigator = LocalNavigator.currentOrThrow()
    val viewModel = koinViewModel<NotesViewModel>()
    LaunchedEffect(Unit) {
        viewModel.uiEvents.collect{ uiEvents->
            when(uiEvents){
                NotesViewModel.UiEvents.NavigateToAllNotesScreen -> {
                    navigator.navigate(HomeNavRoutes.AllNotesScreen)
                }
            }

        }
    }

}

@Composable
fun HomeUiEventsHandler(){
    val navigator = LocalNavigator.currentOrThrow()
    val viewModel = koinViewModel<HomeViewModel>()
    LaunchedEffect(Unit) {
        viewModel.uiEvents.collect{ uiEvents->
            when(uiEvents){
                HomeViewModel.UiEvent.NavigateToAllNotes -> {
                    navigator.navigate(HomeNavRoutes.AllNotesScreen)
                }
            }
        }
    }
}
