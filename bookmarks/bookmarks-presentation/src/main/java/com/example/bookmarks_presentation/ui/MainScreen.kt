package com.example.bookmarks_presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bookmarks_presentation.events.main_events.MainEvents
import com.example.bookmarks_presentation.ui_elements.AddCategoryDialog
import com.example.bookmarks_presentation.ui_events_handlers.main_handler.MainUiEventHandler
import com.example.bookmarks_presentation.view_models.MainViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun MainBookmarksScreen(modifier: Modifier = Modifier) {
    val viewModel = koinViewModel<MainViewModel>()
    val tittleOfCategory by viewModel.tittleOfCategory
    val isDialogShowed by viewModel.isDialogForAddingCategoryIsShowed

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
        Box(
            modifier
                .fillMaxSize()
                .background(Color.Red), contentAlignment = Alignment.BottomCenter
        ) {

            Box(
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .fillMaxHeight(0.9f)
                    .background(Color.Cyan)
            ) {
                Column(modifier = Modifier.fillMaxSize()) {
                    Box(
                        modifier = Modifier
                            .fillMaxHeight(0.3f)
                            .fillMaxWidth()
                            .background(Color.Green)
                    ) {

                    }
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight()
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .fillMaxHeight(0.4f)
                                .background(Color.Magenta),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .height(200.dp)
                                    .width(180.dp)
                                    .background(Color.Transparent)
                                    .clickable {
                                        viewModel.onEvent(MainEvents.ShowDialogForAddingCategory)
                                    }
                                    .border(
                                        width = 1.dp,
                                        color = Color.Black,
                                        shape = RoundedCornerShape(10.dp)
                                    ), contentAlignment = Alignment.Center
                            ) {

                                Text("+", fontSize = 30.sp)
                            }
                            Card(
                                modifier = Modifier
                                    .height(200.dp)
                                    .width(180.dp)
                            ) {
                                Box(
                                    modifier = Modifier.fillMaxSize().clickable {
viewModel.onEvent(MainEvents.NavigateToAllBookmarks)
                                    },
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(text = "All", modifier = Modifier)
                                }

                            }
                        }
                        Box(modifier = Modifier.fillMaxSize()) {
                            if (viewModel.listOfCategories.value != null) {
                                LazyVerticalGrid(
                                    columns = GridCells.Fixed(2),
                                    verticalArrangement = Arrangement.spacedBy(20.dp),
                                    horizontalArrangement = Arrangement.spacedBy(20.dp)
                                ) {
                                    items(viewModel.listOfCategories.value!!.size) {
                                        Card(modifier = Modifier.height(200.dp)) {
                                            Text("dkfkfjk")
                                        }
                                    }
                                }
                            }


                        }
                    }


                }

            }
        }
        if (isDialogShowed) {
            AddCategoryDialog(
                onDismiss = { viewModel.onEvent(MainEvents.HideDialogForAddingCategory) },
                onConfirm = {
                    viewModel.onEvent(MainEvents.AddCategory)
                },
                updateTittle = {
                    viewModel.onEvent(MainEvents.UpdateTittleOfCategory(it))
                },
                tittle = tittleOfCategory
            )
        }

    }

}