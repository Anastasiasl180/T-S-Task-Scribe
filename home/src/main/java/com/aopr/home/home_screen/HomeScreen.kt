package com.aopr.home.home_screen

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
import com.aopr.home.home_screen.navigation.DrawerNavRoutes
import com.aopr.home.home_screen.viewModel.HomeViewModel
import com.aopr.home.home_screen.viewModel.events.HomeEvent
import com.aopr.notes_presentation.view_model.NotesViewModel
import com.aopr.notes_presentation.view_model.events.NotesEvent
import com.aopr.shared_ui.MainViewModel
import com.aopr.shared_ui.util.MainViewModelStoreOwner
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen() {
    BackHandler {

    }
    HomeUiEventHandler()

    val viewModel = koinViewModel<NotesViewModel>()

    val tittleOfNote = viewModel.note.value
    val descriptionOfNote = viewModel.descriptionOfNote.value
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
    var showBottomSheet by remember { mutableStateOf(false) }
    var blurredCardIndex by remember { mutableIntStateOf(-1) }
    val listOfButtons = listOf(
        getNotesButtons(onShowBottomSheetChange = {
            showBottomSheet = it
        }, navigateToAllNotes = {
            viewModel.onEvent(NotesEvent.NavigateToAllNotes)
        }), getTasksButtons(), getBookMarksButtons(),
        getCalendarButton()
    )

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Column(modifier = Modifier.fillMaxSize()) {
                    drawerItems.forEach { items ->

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
            }

            if (showBottomSheet) {
                BottomSheetContent(
                    onDismiss = { showBottomSheet = false },
                    saveNote = {
                        viewModel.onEvent(NotesEvent.SaveNote)
                    }, tittle = tittleOfNote, description = descriptionOfNote, updateDescription = {
                        viewModel.onEvent(NotesEvent.UpdateDescription(it))
                    }, updateTittle = {
                        viewModel.onEvent(NotesEvent.UpdateTittle(it))
                    })
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
internal fun getTasksButtons(): Array<@Composable () -> Unit> {
    return arrayOf<@Composable () -> Unit>({
        Button(onClick = {
        }) {
            Text(text = stringResource(id = R.string.AllTasks))
        }
    }, {
        Button(onClick = {
        }) {
            Text(text = stringResource(id = R.string.NewTask))
        }
    })
}

@Composable
fun getBookMarksButtons(modifier: Modifier = Modifier): Array<@Composable () -> Unit> {
    return arrayOf<@Composable () -> Unit>({
        Button(onClick = {
        }) {
            Text(text = stringResource(id = R.string.AllBookmarks))
        }
    }, {
        Button(onClick = {
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
