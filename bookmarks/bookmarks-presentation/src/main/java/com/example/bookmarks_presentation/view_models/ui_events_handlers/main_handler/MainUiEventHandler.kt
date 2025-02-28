package com.example.bookmarks_presentation.view_models.ui_events_handlers.main_handler

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.aopr.shared_ui.navigation.LocalNavigator
import com.aopr.shared_ui.util.global_view_model.GlobalViewModelStoreOwner
import com.aopr.shared_ui.navigation.currentOrThrow
import com.example.bookmarks_presentation.view_models.events.categories_events.UiCategoriesEvents
import com.example.bookmarks_presentation.navigation.BookmarksNavRoutes
import com.example.bookmarks_presentation.navigation.BookmarksRoutesInfo
import com.example.bookmarks_presentation.view_models.CategoriesViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun MainUiEventHandler() {
    val navigator = LocalNavigator.currentOrThrow()
    val viewModel = koinViewModel<CategoriesViewModel>(viewModelStoreOwner = GlobalViewModelStoreOwner)

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

                UiCategoriesEvents.NavigateBack -> {
                    navigator.popBackStack()
                }
            }

        }
    }
}