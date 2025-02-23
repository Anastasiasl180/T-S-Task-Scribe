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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aopr.shared_ui.cardsView.background
import com.aopr.shared_ui.cardsView.cardViews
import com.aopr.shared_ui.util.MainViewModelStoreOwner
import com.example.bookmarks_presentation.R
import com.example.bookmarks_presentation.events.main_events.MainEvents
import com.example.bookmarks_presentation.ui.ui_elements.AddCategoryDialog
import com.example.bookmarks_presentation.ui_events_handlers.main_handler.MainUiEventHandler
import com.example.bookmarks_presentation.view_models.MainViewModel
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainBookmarksScreen(modifier: Modifier = Modifier) {
    val viewModel = koinViewModel<MainViewModel>(viewModelStoreOwner = MainViewModelStoreOwner)
    val tittleOfCategory by viewModel.tittleOfCategory
    val isDialogShowed by viewModel.isDialogForAddingCategoryIsShowed
    val backgroundTheme = background()
    val list by viewModel.listOfCategories.collectAsState()

    MainUiEventHandler()
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    viewModel.onEvent(
                        MainEvents.NavigateToCreateBookmark(null)
                    )
                }, modifier = Modifier
                    .clip(CircleShape)
                    .size(60.dp), containerColor = Color.DarkGray.copy(alpha = 0.6f)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "AddFloatingActionButton",
                    modifier = Modifier.size(20.dp)
                )


            }
        }, topBar = {
            TopAppBar(colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent),
                navigationIcon = {
                    IconButton(
                        onClick = { },
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(Color.DarkGray.copy(alpha = 0.6f))
                            .size(50.dp)
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Default.KeyboardArrowLeft,
                            contentDescription = "",
                            tint = Color.White, modifier = Modifier.size(20.dp)
                        )
                    }
                },
                title = { /*TODO*/ })
        }

    ) { _ ->

        Box(
            modifier
                .fillMaxSize()
                .background(backgroundTheme), contentAlignment = Alignment.BottomCenter
        ) {

            Box(
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .fillMaxHeight(0.9f)

            ) {
                Column(modifier = Modifier.fillMaxSize()) {
                    Box(
                        modifier = Modifier
                            .fillMaxHeight(0.25f)
                            .fillMaxWidth(), contentAlignment = Alignment.CenterStart

                    ) {
                        Text(text = stringResource(R.string.bookmarks), fontSize = 30.sp)
                    }
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.spacedBy(20.dp)
                        ) {

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(
                                    modifier = Modifier.fillMaxWidth(0.5f),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(100.dp)
                                            .background(Color.Transparent)
                                            .clickable {
                                                viewModel.onEvent(MainEvents.ShowDialogForAddingCategory)
                                            }

                                            .border(
                                                width = 1.dp,
                                                color = Color.Black,
                                                shape = MaterialTheme.shapes.extraLarge
                                            ),
                                        contentAlignment = Alignment.Center,
                                    ) {
                                        Text(
                                            text = stringResource(com.aopr.shared_ui.R.string.plus),
                                            fontSize = 30.sp
                                        )
                                    }
                                }

                                Card(
                                    modifier = Modifier
                                        .height(220.dp)
                                        .width(180.dp), shape = MaterialTheme.shapes.extraLarge,
                                    colors = CardDefaults.cardColors(containerColor = Color.Transparent)
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .background(cardViews())
                                            .clickable {
                                                viewModel.onEvent(MainEvents.NavigateToAllBookmarks)
                                            },
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = stringResource(com.aopr.shared_ui.R.string.all),
                                            modifier = Modifier
                                        )
                                    }

                                }

                            }
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .fillMaxHeight(0.15f),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(text = stringResource(R.string.categories), fontSize = 20.sp)

                            }
                            LazyVerticalGrid(
                                columns = GridCells.Fixed(2),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .fillMaxHeight(0.95f)
                                    .clip(RoundedCornerShape(20.dp)),
                                verticalArrangement = Arrangement.spacedBy(20.dp),
                                horizontalArrangement = Arrangement.spacedBy(20.dp)
                            ) {
                                items(list) { category ->
                                    Card(
                                        modifier = Modifier
                                            .height(220.dp)
                                            .width(180.dp)
                                            .clickable {
                                                viewModel.onEvent(
                                                    MainEvents.NavigateToBookmarksByCategoryId(
                                                        category.id
                                                    )
                                                )
                                            },
                                        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
                                        shape = MaterialTheme.shapes.extraLarge
                                    ) {
                                        Box(
                                            modifier = Modifier
                                                .fillMaxSize()
                                                .background(cardViews()),
                                            contentAlignment = Alignment.Center
                                        ) {

                                            Text(
                                                text = stringResource(com.aopr.shared_ui.R.string.all),
                                                modifier = Modifier
                                            )
                                            Button(onClick = {
                                                viewModel.onEvent(
                                                    MainEvents.DeleteCategory(
                                                        category
                                                    )
                                                )
                                            }) { }
                                            Text(category.tittle)

                                        }
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