package com.example.bookmarks_presentation.view_models.ui_events_handlers.creating_bookmark_handler

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.toRoute
import com.aopr.shared_ui.navigation.LocalNavigator
import com.aopr.shared_ui.navigation.currentOrThrow
import com.example.bookmarks_presentation.view_models.events.creating_bookmark_events.CreatingBookmarkEvents
import com.example.bookmarks_presentation.view_models.events.creating_bookmark_events.CreatingBookmarkUiEvents
import com.example.bookmarks_presentation.navigation.BookmarksNavRoutes
import com.example.bookmarks_presentation.view_models.CreatingBookmarkViewModel
import org.koin.androidx.compose.koinViewModel


@Composable
fun CreatingBookmarkUiEventHandlerWithCategoryId() {
    val viewModel = koinViewModel<CreatingBookmarkViewModel>()
    val navigator = LocalNavigator.currentOrThrow()

    val bookmarkInfo =
        navigator.currentBackStackEntry!!.toRoute<BookmarksNavRoutes.CreatingBookMarkScreen>()
    val bookmarkId = bookmarkInfo.bookmarksInfo

LaunchedEffect(Unit) {
    viewModel.event.collect {event->
        when(event){
            CreatingBookmarkUiEvents.NavigateBack -> {
                navigator.popBackStack()
            }
        }

    }
}
    LaunchedEffect(Unit) {
        viewModel.onEvent(CreatingBookmarkEvents.GetBookmarkById(bookmarkId))
    }

}


