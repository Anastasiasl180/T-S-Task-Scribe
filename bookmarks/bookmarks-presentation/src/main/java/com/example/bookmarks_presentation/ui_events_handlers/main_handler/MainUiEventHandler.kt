package com.example.bookmarks_presentation.ui_events_handlers.main_handler

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.aopr.shared_ui.util.LocalNavigator
import com.aopr.shared_ui.util.currentOrThrow
import com.example.bookmarks_presentation.events.main_events.UiMainEvents
import com.example.bookmarks_presentation.navigation.BookmarksNavRoutes
import com.example.bookmarks_presentation.navigation.BookmarksRoutesInfo
import com.example.bookmarks_presentation.view_models.MainViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun MainUiEventHandler() {
    val navigator = LocalNavigator.currentOrThrow()
    val viewModel = koinViewModel<MainViewModel>()

    LaunchedEffect(Unit){
        viewModel.event.collect {uiEvent->
            when(uiEvent){
                is UiMainEvents.NavigateToCreateBookmark -> {
                    Log.wtf("dfhf", uiEvent.id.toString(), )
                    navigator.navigate(BookmarksNavRoutes.CreatingBookMarkScreen(BookmarksRoutesInfo(bookmarkId = uiEvent.id)))
                }

                UiMainEvents.NavigateToAllBookMarks -> {
                navigator.navigate(BookmarksNavRoutes.AllBookmarksScreen)
                }

                is UiMainEvents.NavigateToBookmarksByCategoryId -> {
                     navigator.navigate(BookmarksNavRoutes.AllBookmarksInCategory(uiEvent.id))
                }
            }

        }
    }
}