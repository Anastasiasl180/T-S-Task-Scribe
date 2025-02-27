package com.example.bookmarks_presentation.ui_events_handlers.main_handler

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.aopr.shared_ui.util.LocalNavigator
import com.aopr.shared_ui.util.MainViewModelStoreOwner
import com.aopr.shared_ui.util.currentOrThrow
import com.example.bookmarks_presentation.events.categories_events.UiCategoriesEvents
import com.example.bookmarks_presentation.navigation.BookmarksNavRoutes
import com.example.bookmarks_presentation.navigation.BookmarksRoutesInfo
import com.example.bookmarks_presentation.view_models.CategoriesViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun MainUiEventHandler() {
    val navigator = LocalNavigator.currentOrThrow()
    val viewModel = koinViewModel<CategoriesViewModel>(viewModelStoreOwner = MainViewModelStoreOwner)

    LaunchedEffect(Unit){
        viewModel.event.collect {uiEvent->
            when(uiEvent){
                is UiCategoriesEvents.NavigateToCreateBookmark -> {
                    navigator.navigate(BookmarksNavRoutes.CreatingBookMarkScreen(BookmarksRoutesInfo(bookmarkId = uiEvent.id)))
                }

                UiCategoriesEvents.NavigateToAllBookMarks -> {
                navigator.navigate(BookmarksNavRoutes.AllBookmarksScreen)
                }

                is UiCategoriesEvents.NavigateToBookmarksByCategoryId -> {
                     navigator.navigate(BookmarksNavRoutes.AllBookmarksInCategory(uiEvent.id))
                }
            }

        }
    }
}