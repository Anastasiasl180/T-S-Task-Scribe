package com.example.bookmarks_presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.bookmarks_presentation.events.all_bookmarks_in_category_event.AllBookmarksInCategoryEvents
import com.example.bookmarks_presentation.ui_events_handlers.all_bookmarks_in_category_handler.AllBookmarksByCategoryUiEventHandler
import com.example.bookmarks_presentation.view_models.AllBookmarksInCategoryViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun AllBookmarksInCategory() {

    AllBookmarksByCategoryUiEventHandler()
    val viewModel = koinViewModel<AllBookmarksInCategoryViewModel>()
    Scaffold(floatingActionButton = {
        FloatingActionButton(onClick = {
            viewModel.onEvent(AllBookmarksInCategoryEvents.NavigateToCreateBookmarkWithCategoryId)
        }) {
            Text("+")
        }
    }) { _ ->


        val listOfBookmarks = viewModel.listOfBookmarks.collectAsState()
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Blue),
            contentAlignment = Alignment.Center
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                if (listOfBookmarks.value.isNotEmpty()) {
                    listOfBookmarks.value.forEach { it ->
                        Card(
                            modifier = Modifier
                                .height(100.dp)
                                .width(100.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxSize(),
                                verticalAlignment = Alignment.Bottom
                            ) {
                                Button(
                                    onClick = {
                                        viewModel.onEvent(
                                            AllBookmarksInCategoryEvents.DeleteBookmark(
                                                it
                                            )
                                        )
                                    }) {
                                    Text("delete")
                                }

                                Text(it.tittle)
                            }
                        }

                    }
                } else {
                    Text("NOOOOOOOOO")
                }
            }
        }
    }
}