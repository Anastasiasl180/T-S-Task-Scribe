package com.example.bookmarks_presentation.ui

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aopr.shared_ui.cardsView.CircularCheckbox
import com.aopr.shared_ui.cardsView.background
import com.aopr.shared_ui.cardsView.cardViews
import com.aopr.shared_ui.deletion_row.DeletionRow
import com.aopr.shared_ui.util.global_view_model.GlobalViewModelStoreOwner
import com.example.bookmarks_domain.models.Category
import com.example.bookmarks_presentation.R
import com.example.bookmarks_presentation.view_models.events.categories_events.CategoriesEvents
import com.example.bookmarks_presentation.ui.ui_elements.AddCategoryDialog
import com.example.bookmarks_presentation.view_models.ui_events_handlers.main_handler.MainUiEventHandler
import com.example.bookmarks_presentation.view_models.CategoriesViewModel
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainBookmarksScreen() {
    val viewModel =
        koinViewModel<CategoriesViewModel>(viewModelStoreOwner = GlobalViewModelStoreOwner)
    val tittleOfCategory by viewModel.tittleOfCategory
    val isDialogShowed by viewModel.isDialogForAddingCategoryIsShowed
    val backgroundTheme = background()
    val isInSelectionModeForDelete = viewModel.isInSelectedMode.collectAsState()
    val list by viewModel.listOfCategories.collectAsState()

    MainUiEventHandler()
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    viewModel.onEvent(
                        CategoriesEvents.NavigateToCreateBookmark(null)
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
                        onClick = { viewModel.onEvent(CategoriesEvents.NavigateBack) },
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
            modifier = Modifier
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
                        Text(
                            text = stringResource(R.string.bookmarks),
                            fontSize = 30.sp,
                            fontFamily = FontFamily(
                                Font(com.aopr.shared_ui.R.font.open_sans_light)
                            )
                        )
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
                                                viewModel.onEvent(CategoriesEvents.ShowDialogForAddingCategory)
                                            }

                                            .border(
                                                width = 1.dp,
                                                color = Color.White,
                                                shape = MaterialTheme.shapes.extraLarge
                                            ),
                                        contentAlignment = Alignment.Center,
                                    ) {
                                        Text(
                                            text = stringResource(com.aopr.shared_ui.R.string.plus),
                                            fontSize = 30.sp,
                                            fontFamily = FontFamily(Font(com.aopr.shared_ui.R.font.open_sans_light))

                                        )
                                    }
                                }

                                Card(
                                    modifier = Modifier
                                        .height(220.dp)
                                        .width(180.dp)
                                        .border(
                                            border = BorderStroke(
                                                0.5.dp,
                                                color = Color.White.copy(alpha = 0.1f)
                                            ),
                                            shape = MaterialTheme.shapes.extraLarge
                                        ),
                                    shape = MaterialTheme.shapes.extraLarge,
                                    colors = CardDefaults.cardColors(containerColor = Color.Transparent),
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .background(cardViews())
                                            .clickable {
                                                viewModel.onEvent(CategoriesEvents.NavigateToAllBookmarks)
                                            },
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = stringResource(com.aopr.shared_ui.R.string.all),
                                            fontFamily = FontFamily(Font(com.aopr.shared_ui.R.font.open_sans_light)),
                                            modifier = Modifier
                                        )
                                    }

                                }

                            }
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .fillMaxHeight(0.15f),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = stringResource(R.string.categories),
                                    fontFamily = FontFamily(Font(com.aopr.shared_ui.R.font.open_sans_light)),
                                    fontSize = 20.sp
                                )
                                if (list.isNotEmpty()) {
                                    DeletionRow(
                                        isInSelectionModeForDelete.value,
                                        turnOnSelectionMode = { viewModel.onEvent(CategoriesEvents.TurnOnSelectionModeForDelete) },
                                        deleteChosenItems = { viewModel.onEvent(CategoriesEvents.DeleteSeveralCategories) })
                                }
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
                                    CategoryCard(
                                        category,
                                        category.tittle,
                                        isInSelectionMode = isInSelectionModeForDelete.value,
                                        goToCategory = {
                                            viewModel.onEvent(
                                                CategoriesEvents.NavigateToBookmarksByCategoryId(
                                                    category.id
                                                )
                                            )
                                        },
                                        onSelectCategory = { selectedCategory, isOnSelected ->
                                            if (isOnSelected) {
                                                viewModel.onEvent(
                                                    CategoriesEvents.AddCategoryForDeletion(
                                                        selectedCategory
                                                    )
                                                )
                                            } else {
                                                viewModel.onEvent(
                                                    CategoriesEvents.RemoveCategoryForDeletion(
                                                        selectedCategory
                                                    )
                                                )
                                            }
                                        })

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
            onDismiss = { viewModel.onEvent(CategoriesEvents.HideDialogForAddingCategory) },
            onConfirm = {
                viewModel.onEvent(CategoriesEvents.AddCategory)
            },
            updateTittle = {
                viewModel.onEvent(CategoriesEvents.UpdateTittleOfCategory(it))
            },
            tittle = tittleOfCategory
        )
    }

}


@Composable
fun CategoryCard(
    category: Category,
    tittle: String,
    isInSelectionMode: Boolean,
    goToCategory: () -> Unit,
    onSelectCategory: (Category, Boolean) -> Unit
) {

    val isSelected = remember(category) { mutableStateOf(false) }

    ElevatedCard(
        modifier = Modifier
            .height(220.dp)
            .width(180.dp)
            .clickable { goToCategory() },
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        shape = MaterialTheme.shapes.extraLarge,
    ) {

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(cardViews()), contentAlignment = Alignment.Center
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.3f),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(text = tittle)
            }
            Box(
                modifier = Modifier
                    .fillMaxHeight(0.8f)
                    .fillMaxWidth(0.9f), contentAlignment = Alignment.BottomEnd
            ) {
                Row(
                    modifier = Modifier,
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (isInSelectionMode) {
                        CircularCheckbox(checked = isSelected.value, onCheckedChange = { checked ->
                            isSelected.value = checked
                            onSelectCategory(category, checked)
                        }, circleSize = 25.dp)
                    }
                }
            }

        }
    }

}
