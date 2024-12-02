package com.example.bookmarks_presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.bookmarks_presentation.view_models.AllBookmarksViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun AllBookmarksScreen(modifier: Modifier = Modifier) {
val viewModel = koinViewModel<AllBookmarksViewModel>()
    Box(modifier.fillMaxSize().background(Color.Green), contentAlignment = Alignment.Center) {
        Column(modifier = Modifier.fillMaxSize()) {
            viewModel.listOfBookmarks.value?.forEach {
                Card(modifier = Modifier.height(200.dp)) { Text(it.tittle) }
            }
        }
    }
}