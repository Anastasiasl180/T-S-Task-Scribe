package com.aopr.home.home_screen.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.aopr.home.R
import com.aopr.home.home_screen.navigation.DrawerItems
import com.aopr.home.home_screen.viewModel.events.HomeUiEventHandler
import com.aopr.home.home_screen.viewModel.events.HomeViewModel
import com.aopr.home.home_screen.viewModel.events.homeEvents.HomeEvent
import com.radusalagean.infobarcompose.InfoBar
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen() {
    BackHandler {

    }
    HomeUiEventHandler()

    val viewModel = koinViewModel<HomeViewModel>()

    val tittleOfNote = viewModel.tittleOfNote.value
    val descriptionOfNote = viewModel.descriptionOfNote.value
    val tittleOfTask = viewModel.tittleOfTask.value
    val descriptionOfTask = viewModel.descriptionOfTask.value
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val cardTexts = listOf(
        stringResource(id = R.string.Notes),
        stringResource(id = R.string.Tasks),
        stringResource(id = R.string.Bookmarks),
        stringResource(id = R.string.Calendar)
    )
    val drawerItems = remember {
        DrawerItems.entries
    }
    var showBottomSheetForNotes by remember { mutableStateOf(false) }
    var showBottomSheetForTasks by remember { mutableStateOf(false) }
    var showBottomSheetForBookmarks by remember { mutableStateOf(false) }
    var blurredCardIndex by remember { mutableIntStateOf(-1) }
    val listOfButtons = listOf(
        getNotesButtons(onShowBottomSheetChange = {
            showBottomSheetForNotes = it
        }, navigateToAllNotes = {
            viewModel.onEvent(HomeEvent.NavigateToAllNotes)
        }), getTasksButtons(navigateToAllTasks = {
            viewModel.onEvent(HomeEvent.NavigateToAllTasks)
        }, onShowBottomSheetChange = {
            showBottomSheetForTasks = it
        }), getBookMarksButtons(navigateToAllCategoriesOfBookmarks = {
            viewModel.onEvent(HomeEvent.NavigateToAllCategoriesOfBookmarks)
        }, onShowBottomSheetChange = {
            showBottomSheetForBookmarks = it
        }),
        getCalendarButton()
    )

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Column(modifier = Modifier.fillMaxSize()) {
                    drawerItems.forEach { items ->

                        Row(modifier = Modifier.fillMaxWidth()) {
                            TextButton(onClick = {
                                viewModel.onEvent(HomeEvent.NavigateToThemesByDrawer)
                            }) {
                                Text(items.name)
                            }
                        }
                    }
                }
            }
        }) {
        Scaffold(modifier = Modifier.consumeWindowInsets(WindowInsets.ime)) { _ ->
            Box(
                modifier = Modifier
                    .consumeWindowInsets(WindowInsets.ime)
                    .fillMaxSize()
            ) {
                Column(modifier = Modifier.fillMaxSize()) {
                    Box(
                        modifier = Modifier
                            .fillMaxHeight(0.2f)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .fillMaxHeight(0.5f),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            IconButton(
                                onClick = {
                                    scope.launch {
                                        drawerState.apply {
                                            if (isClosed) open() else close()
                                        }
                                    }
                                },
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Menu,
                                    contentDescription = "drawer"
                                )
                            }
                        }
                    }
                    Box(
                        modifier = Modifier
                            .fillMaxHeight(0.8f)
                            .fillMaxWidth(), contentAlignment = Alignment.Center
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
                                items(4) { index ->
                                    val isBlurred = blurredCardIndex == index
                                    HomeCard(
                                        isBlurred = isBlurred, cardText = cardTexts[index],
                                        onCardClick = {
                                            blurredCardIndex =
                                                if (blurredCardIndex == index) -1 else index
                                        },
                                        buttons = listOfButtons[index]
                                    )
                                }
                            }
                        }

                    }
                }
                InfoBar(offeredMessage = viewModel.infoBar.value) {

                }
            }


            if (showBottomSheetForNotes) {
                BottomSheetContent(
                    onDismiss = { showBottomSheetForNotes = false },
                    saveNote = {
                        viewModel.onEvent(HomeEvent.SaveNote)
                    }, tittle = tittleOfNote, description = descriptionOfNote, updateDescription = {
                        viewModel.onEvent(HomeEvent.UpdateDescriptionOfNote(it))
                    }, updateTittle = {
                        viewModel.onEvent(HomeEvent.UpdateTittleOfNote(it))
                    }, infoBarMessage = viewModel.infoBar.value
                )
            }
            if (showBottomSheetForTasks) {
                BottomSheetContentForTasks(
                    tittle = tittleOfTask,
                    description = descriptionOfTask,
                    onDismiss = { showBottomSheetForTasks = false },
                    saveTask = { viewModel.onEvent(HomeEvent.SaveTask) },
                    updateTittle = { viewModel.onEvent(HomeEvent.UpdateTittleOFTask(it)) },
                    updateDescription = { viewModel.onEvent(HomeEvent.UpdateDescriptionOfTask(it)) },
                    infoBarMessage = viewModel.infoBar.value
                )
            }

        }
    }

}

@Composable
fun getNotesButtons(
    onShowBottomSheetChange: (Boolean) -> Unit,
    navigateToAllNotes: () -> Unit
): Array<@Composable () -> Unit> {
    return arrayOf<@Composable () -> Unit>({
        Button(onClick = {
            navigateToAllNotes()
        }) {
            Text(text = stringResource(id = R.string.AllNotes))
        }
    }, {
        Button(onClick = {
            onShowBottomSheetChange(true)
        }) {
            Text(text = stringResource(id = R.string.NewNote))
        }
    })
}

@Composable
internal fun getTasksButtons(
    navigateToAllTasks: () -> Unit,
    onShowBottomSheetChange: (Boolean) -> Unit
): Array<@Composable () -> Unit> {
    return arrayOf<@Composable () -> Unit>({
        Button(onClick = {
            navigateToAllTasks()
        }) {
            Text(text = stringResource(id = R.string.AllTasks))
        }
    }, {
        Button(onClick = {
            onShowBottomSheetChange(true)
        }) {
            Text(text = stringResource(id = R.string.NewTask))
        }
    })
}

@Composable
fun getBookMarksButtons(
    navigateToAllCategoriesOfBookmarks: () -> Unit,
    onShowBottomSheetChange: (Boolean) -> Unit
): Array<@Composable () -> Unit> {
    return arrayOf<@Composable () -> Unit>({
        Button(onClick = {
            navigateToAllCategoriesOfBookmarks()
        }) {
            Text(text = stringResource(id = R.string.AllBookmarks))
        }
    }, {
        Button(onClick = {
            onShowBottomSheetChange(true)
        }) {
            Text(text = stringResource(id = R.string.NewBookmark))
        }
    })
}

@Composable
fun getCalendarButton(modifier: Modifier = Modifier): Array<@Composable () -> Unit> {
    return arrayOf<@Composable () -> Unit>({
        Button(onClick = {
        }) {
            Text(text = stringResource(id = R.string.AllEvents))
        }
    })
}
