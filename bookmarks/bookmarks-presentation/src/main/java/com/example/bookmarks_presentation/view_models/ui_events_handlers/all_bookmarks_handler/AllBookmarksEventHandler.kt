package com.example.bookmarks_presentation.view_models.ui_events_handlers.all_bookmarks_handler

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.aopr.shared_ui.navigation.LocalNavigator
import com.aopr.shared_ui.navigation.currentOrThrow
import com.example.bookmarks_presentation.view_models.events.all_bookmarks_event.AllBookmarksUiEvents
import com.example.bookmarks_presentation.navigation.BookmarksNavRoutes
import com.example.bookmarks_presentation.navigation.BookmarksRoutesInfo
import com.example.bookmarks_presentation.view_models.AllBookmarksViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun AllBookmarksUiEventHandler() {
    val viewModel = koinViewModel<AllBookmarksViewModel>()
    val navigator = LocalNavigator.currentOrThrow()

    LaunchedEffect(Unit) {
        viewModel.event.collect { uiEvent ->
            when (uiEvent) {
                is AllBookmarksUiEvents.NavigateToBookmarkById -> {
                    navigator.navigate(
                        BookmarksNavRoutes.CreatingBookMarkScreen(
                            BookmarksRoutesInfo(
                                bookmarkId = uiEvent.id
                            )
                        )
                    )
                }

                AllBookmarksUiEvents.NavigateBack -> {
                    navigator.popBackStack()
                }

                AllBookmarksUiEvents.NavigateToCreateBookmarkScreen -> {
                    navigator.navigate(
                        BookmarksNavRoutes.CreatingBookMarkScreen(
                            BookmarksRoutesInfo(
                                bookmarkId = null
                            )
                        )
                    )
                }
            }

        }
    }
}