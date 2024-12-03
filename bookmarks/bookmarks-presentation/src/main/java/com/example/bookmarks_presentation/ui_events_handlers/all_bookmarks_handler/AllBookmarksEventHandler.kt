package com.example.bookmarks_presentation.ui_events_handlers.all_bookmarks_handler

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import com.aopr.shared_ui.util.LocalNavigator
import com.aopr.shared_ui.util.currentOrThrow
import com.example.bookmarks_presentation.events.all_bookmarks_event.AllBookmarksUiEvents
import com.example.bookmarks_presentation.navigation.AllBookmarksNavRoutes
import com.example.bookmarks_presentation.view_models.AllBookmarksViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun AllBookmarksUiEventHandler() {
    val viewModel = koinViewModel<AllBookmarksViewModel>()
    val navigator = LocalNavigator.currentOrThrow()

    LaunchedEffect(Unit) {
        viewModel.event.collect {uiEvent->
            when(uiEvent){
                is AllBookmarksUiEvents.NavigateToBookmarkById -> {
                    navigator.navigate(AllBookmarksNavRoutes.CreatingBookmarkScreen(uiEvent.id))
                }
            }

        }
    }
}