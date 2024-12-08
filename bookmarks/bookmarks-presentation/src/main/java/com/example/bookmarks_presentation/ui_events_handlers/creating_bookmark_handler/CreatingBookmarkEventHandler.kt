package com.example.bookmarks_presentation.ui_events_handlers.creating_bookmark_handler

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.toRoute
import com.aopr.shared_ui.util.LocalNavigator
import com.aopr.shared_ui.util.currentOrThrow
import com.example.bookmarks_presentation.events.creating_bookmark_events.CreatingBookmarkEvents
import com.example.bookmarks_presentation.navigation.AllBookmarksByCategoryNavRoutes
import com.example.bookmarks_presentation.navigation.AllBookmarksNavRoutes
import com.example.bookmarks_presentation.view_models.CreatingBookmarkViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun CreatingBookmarkUiEventHandlerWithBookmarkId() {
    val viewModel = koinViewModel<CreatingBookmarkViewModel>()
    val navigator = LocalNavigator.currentOrThrow()

    val id =
        navigator.currentBackStackEntry?.toRoute<AllBookmarksNavRoutes.CreatingBookmarkScreen>()
    val bookmarkId = id?.id

    LaunchedEffect(Unit) {
        viewModel.oEvent(CreatingBookmarkEvents.GetBookmarkById(bookmarkId))
    }
}

@Composable
fun CreatingBookmarkUiEventHandlerWithCategoryId() {
    val viewModel = koinViewModel<CreatingBookmarkViewModel>()
    val navigator = LocalNavigator.currentOrThrow()

    val id =
        navigator.currentBackStackEntry?.toRoute<AllBookmarksByCategoryNavRoutes.CreatingBookmarkWithCategoryId>()
    val categoryId = id?.id


    LaunchedEffect(Unit) {
        viewModel.oEvent(CreatingBookmarkEvents.GetNewBookmarkWithCategoryId(categoryId))
    }
}