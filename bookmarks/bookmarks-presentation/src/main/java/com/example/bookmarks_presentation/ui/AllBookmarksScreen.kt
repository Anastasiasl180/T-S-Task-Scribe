package com.example.bookmarks_presentation.ui

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.bookmarks_presentation.events.all_bookmarks_event.AllBookmarksEvents
import com.example.bookmarks_presentation.ui_elements.CustomCard
import com.example.bookmarks_presentation.ui_events_handlers.all_bookmarks_handler.AllBookmarksUiEventHandler
import com.example.bookmarks_presentation.view_models.AllBookmarksViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun AllBookmarksScreen(modifier: Modifier = Modifier) {
    val viewModel = koinViewModel<AllBookmarksViewModel>()
    AllBookmarksUiEventHandler()
    Box(
        modifier
            .fillMaxSize()
            .background(Color.DarkGray), contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(0.9f), contentAlignment = Alignment.Center
        ) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                verticalArrangement = Arrangement.spacedBy(20.dp),
                horizontalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                if (viewModel.listOfBookmarks.value != null) {
                    items(viewModel.listOfBookmarks.value!!) {
                        CustomCard(
                            modifier = Modifier
                                .width(120.dp)
                                .height(180.dp), navigateToBookmark = {
                                viewModel.onEvent(AllBookmarksEvents.NavigateToBookmarkById(it.id))
                            }
                        )

                    }
                }


            }
        }

    }
}