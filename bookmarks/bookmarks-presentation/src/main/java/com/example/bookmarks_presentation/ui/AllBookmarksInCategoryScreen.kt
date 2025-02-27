package com.example.bookmarks_presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aopr.shared_ui.cardsView.background
import com.aopr.shared_ui.deletion_row.DeletionRow
import com.aopr.shared_ui.top_app_bar.searchBarScrollBehaviour
import com.example.bookmarks_presentation.R
import com.example.bookmarks_presentation.events.all_bookmarks_in_category_event.AllBookmarksInCategoryEvents
import com.example.bookmarks_presentation.ui.ui_elements.CustomCard
import com.example.bookmarks_presentation.ui_events_handlers.all_bookmarks_in_category_handler.AllBookmarksByCategoryUiEventHandler
import com.example.bookmarks_presentation.view_models.AllBookmarksInCategoryViewModel
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AllBookmarksInCategory() {


    AllBookmarksByCategoryUiEventHandler()
    val viewModel = koinViewModel<AllBookmarksInCategoryViewModel>()
    val listOfBookmarks = viewModel.listOfBookmarks.collectAsState()
    val isInSelectionMode = viewModel.isInSelectedMode.collectAsState()
    val backgroundTheme = background()

    val topAppBarDefaults = searchBarScrollBehaviour()
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    viewModel.onEvent(AllBookmarksInCategoryEvents.NavigateToCreateBookmarkWithCategoryId)

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
            TopAppBar(colors = TopAppBarDefaults.mediumTopAppBarColors(
                containerColor = Color.Transparent,
                scrolledContainerColor = Color.Transparent
            ), scrollBehavior = topAppBarDefaults,
                navigationIcon = {
                    IconButton(
                        onClick = { viewModel.onEvent(AllBookmarksInCategoryEvents.NavigateBack) },
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
                title = { /*TODO*/
                })
        }

    ) { _ ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(backgroundTheme),
            contentAlignment = Alignment.BottomCenter
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight(0.9f)
                    .fillMaxWidth(0.9f), contentAlignment = Alignment.Center
            ) {
                Column(modifier = Modifier.fillMaxSize()) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(0.25f),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = stringResource(R.string.all_bookmarks),
                            fontSize = 30.sp,
                            fontFamily = FontFamily(
                                Font(com.aopr.shared_ui.R.font.open_sans_light)
                            )
                        )
                        if (listOfBookmarks.value != null){
                            DeletionRow(
                                isInSelectionMode.value,
                                turnOnSelectionMode = { viewModel.onEvent(AllBookmarksInCategoryEvents.TurnOnSelectionModeForDelete) },
                                deleteChosenItems = {
                                    viewModel.deleteChosenCategories()
                                })
                        }

                    }
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxHeight(0.95f)
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(20.dp)),
                        verticalArrangement = Arrangement.spacedBy(20.dp)
                    ) {

                        if (listOfBookmarks.value != null) {
                            items(listOfBookmarks.value!!) {
                                Box(
                                    modifier = Modifier
                                        .height(250.dp)
                                        .fillMaxWidth()
                                ) {
                                    CustomCard(
                                        modifier = Modifier.fillMaxSize(),
                                        navigateToBookmark = {
                                            viewModel.onEvent(
                                                AllBookmarksInCategoryEvents.NavigateToBookmarkById(
                                                    it.id
                                                )
                                            )
                                        },
                                        deleteBookmark = {
                                            viewModel.onEvent(
                                                AllBookmarksInCategoryEvents.DeleteBookmark(
                                                    it
                                                )
                                            )
                                        },
                                        tittleOfBookmark = it.tittle,
                                        bookmark = it,
                                        isInSelectionMode = isInSelectionMode.value,
                                        isOnSelectedBookmark = { bookmark, isSelected ->
                                            if (isSelected) {
                                                viewModel.addBookmarkToDelete(bookmark)
                                            } else {
                                                viewModel.removeBookmarkFromDeletion(bookmark)
                                            }
                                        }
                                    )
                                }
                            }


                        }

                    }


                }
            }
        }
    }

}

