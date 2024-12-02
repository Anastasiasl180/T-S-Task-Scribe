package com.example.bookmarks_presentation.ui_events_handlers.main_handler

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import com.aopr.shared_ui.util.LocalNavigator
import com.aopr.shared_ui.util.currentOrThrow
import com.example.bookmarks_presentation.events.main_events.UiMainEvents
import com.example.bookmarks_presentation.navigation.AllCategoriesOfBookmarksNavRoutes
import com.example.bookmarks_presentation.view_models.MainViewModel
import kotlinx.coroutines.flow.collect
import org.koin.androidx.compose.koinViewModel

@Composable
fun MainUiEventHandler() {
    val navigator = LocalNavigator.currentOrThrow()
    val viewModel = koinViewModel<MainViewModel>()

    LaunchedEffect(Unit){
        viewModel.event.collect {uiEvent->
            when(uiEvent){
                is UiMainEvents.NavigateToCreateBookmark -> {
                    navigator.navigate(AllCategoriesOfBookmarksNavRoutes.CreatingBookMarkScreen(uiEvent.id))
                }
            }

        }
    }
}