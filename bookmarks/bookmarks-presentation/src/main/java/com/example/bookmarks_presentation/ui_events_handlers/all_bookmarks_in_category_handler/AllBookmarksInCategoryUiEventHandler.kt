package com.example.bookmarks_presentation.ui_events_handlers.all_bookmarks_in_category_handler

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.toRoute
import com.aopr.shared_ui.util.LocalNavigator
import com.aopr.shared_ui.util.currentOrThrow
import com.example.bookmarks_presentation.events.all_bookmarks_in_category_event.AllBookmarksInCategoryEvents
import com.example.bookmarks_presentation.events.all_bookmarks_in_category_event.AllBookmarksInCategoryUiEvents
import com.example.bookmarks_presentation.navigation.BookmarksNavRoutes
import com.example.bookmarks_presentation.navigation.BookmarksRoutesInfo
import com.example.bookmarks_presentation.view_models.AllBookmarksInCategoryViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun AllBookmarksByCategoryUiEventHandler() {
    val viewModel = koinViewModel<AllBookmarksInCategoryViewModel>()
    val navigator = LocalNavigator.currentOrThrow()
    val id =
        navigator.currentBackStackEntry?.toRoute<BookmarksNavRoutes.AllBookmarksInCategory>()
    val idd = id?.id

    LaunchedEffect(idd) {
        viewModel.onEvent(AllBookmarksInCategoryEvents.GetAllBookmarksByCategoryId(idd))
    }
    LaunchedEffect(Unit) {
        viewModel.event.collect { uiEvent ->
            when (uiEvent) {
                is AllBookmarksInCategoryUiEvents.NavigateToCreateBookmarkWithCategoryId -> {
                    navigator.navigate(
                        BookmarksNavRoutes.CreatingBookMarkScreen(
                            BookmarksRoutesInfo(categoryId = uiEvent.id)
                        )
                    )
                }

                AllBookmarksInCategoryUiEvents.NavigateBack -> {
                    navigator.popBackStack()
                }

                is AllBookmarksInCategoryUiEvents.NavigateToBookmarkById -> {
                    navigator.navigate(
                        BookmarksNavRoutes.CreatingBookMarkScreen(
                            BookmarksRoutesInfo(
                                bookmarkId = uiEvent.id
                            )
                        )
                    )
                }
            }

        }
    }

}