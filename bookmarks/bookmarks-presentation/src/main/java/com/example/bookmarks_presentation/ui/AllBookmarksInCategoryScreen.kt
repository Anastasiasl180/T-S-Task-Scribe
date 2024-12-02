package com.example.bookmarks_presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.bookmarks_presentation.ui_events_handlers.main_handler.MainUiEventHandler

@Composable
fun AllBookmarksInCategory() {
    MainUiEventHandler()
    Box(modifier = Modifier.fillMaxSize().background(Color.Blue), contentAlignment = Alignment.Center){
        Text("nananan")
    }
}