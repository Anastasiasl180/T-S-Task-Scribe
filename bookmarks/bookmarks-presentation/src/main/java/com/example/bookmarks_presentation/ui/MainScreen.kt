package com.example.bookmarks_presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.bookmarks_presentation.events.main_events.MainEvents
import com.example.bookmarks_presentation.ui_events_handlers.main_handler.MainUiEventHandler
import com.example.bookmarks_presentation.view_models.MainViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun MainBookmarksScreen(modifier: Modifier = Modifier) {
    val viewModel = koinViewModel<MainViewModel>()
    MainUiEventHandler()
    Scaffold(modifier = Modifier.fillMaxSize(), floatingActionButton = {
        FloatingActionButton(onClick = {
            viewModel.onEvent(
                MainEvents.NavigateToCreateBookmark(null)
            )
        }) {
            Text("+")
        }
    }) { _ ->
        Box(modifier.fillMaxSize().background(Color.Red), contentAlignment = Alignment.Center) {
            Text("xjhfdkjhjdfhjdf")
        }
    }

}