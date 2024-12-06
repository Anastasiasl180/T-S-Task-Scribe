package com.example.bookmarks_presentation.ui_events_handlers.all_bookmarks_in_category_handler

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.toRoute
import com.aopr.shared_ui.navigation.MainNavRoutes
import com.aopr.shared_ui.util.LocalNavigator
import com.aopr.shared_ui.util.currentOrThrow
import com.example.bookmarks_presentation.events.all_bookmarks_in_category_event.AllBookmarksInCategoryEvents
import com.example.bookmarks_presentation.events.all_bookmarks_in_category_event.AllBookmarksInCategoryUiEvents
import com.example.bookmarks_presentation.events.creating_bookmark_events.CreatingBookmarkEvents
import com.example.bookmarks_presentation.navigation.AllBookmarksByCategoryNavRoutes
import com.example.bookmarks_presentation.navigation.AllBookmarksNavRoutes
import com.example.bookmarks_presentation.navigation.AllCategoriesOfBookmarksNavRoutes
import com.example.bookmarks_presentation.view_models.AllBookmarksInCategoryViewModel
import com.example.bookmarks_presentation.view_models.AllBookmarksViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun AllBookmarksByCategoryUiEventHandler() {
    val viewModel = koinViewModel<AllBookmarksInCategoryViewModel>()
    val navigator = LocalNavigator.currentOrThrow()
    val id =
        navigator.currentBackStackEntry?.toRoute<AllCategoriesOfBookmarksNavRoutes.AllBookmarksInCategory>()
    val idd = id?.id

    LaunchedEffect(idd) {
        viewModel.onEvent(AllBookmarksInCategoryEvents.GetAllBookmarksByCategoryId(idd))
    }
    LaunchedEffect(Unit) {
        viewModel.event.collect { uiEvent ->
            when (uiEvent) {
                is AllBookmarksInCategoryUiEvents.NavigateToCreateBookmarkWithCategoryId -> {
                    navigator.navigate(
                        AllBookmarksByCategoryNavRoutes.CreatingBookmarkWithCategoryId(
                            uiEvent.id
                        )
                    )
                }
            }

        }
    }

}