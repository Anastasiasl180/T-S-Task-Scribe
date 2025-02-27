package com.aopr.tasks_presentation.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aopr.notes_presentation.R
import com.aopr.shared_ui.cardsView.CircularCheckbox
import com.aopr.shared_ui.cardsView.background
import com.aopr.shared_ui.cardsView.cardViews
import com.aopr.shared_ui.deletion_row.DeletionRow
import com.aopr.tasks_domain.models.Task
import com.aopr.tasks_presentation.events.all_tasks_events.AllTasksEvents
import com.aopr.tasks_presentation.ui.ui_elements.TaskCard
import com.aopr.tasks_presentation.view_models.AllTasksViewModel
import com.aopr.tasks_presentation.view_models.ui_event_handlers.AllTasksUiEventHandler
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AllTasksScreen() {
    val viewModel = koinViewModel<AllTasksViewModel>()
    BackHandler {

    }
    AllTasksUiEventHandler()
    val listOfNotes = viewModel.listOfTasks.value
    val isInSelectionMode by viewModel.isInSelectedMode.collectAsState()
    val backgroundTheme = background()
    BackHandler {

    }
    Scaffold(

        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    viewModel.onEvent(
                        AllTasksEvents.NavigateToCreatingTaskScreen(
                            null
                        )
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
                        onClick = { viewModel.onEvent(AllTasksEvents.NavigateBack) },
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
                .background(backgroundTheme), contentAlignment = Alignment.Center
        ) {

            Box(
                modifier = Modifier
                    .fillMaxHeight(0.9f)
                    .fillMaxWidth(0.9f)
            ) {
                Column(modifier = Modifier.fillMaxSize()) {
                    Spacer(modifier = Modifier.height(70.dp))
                   Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(0.08f),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = stringResource(
                                id = R.string.tasks
                            ),
                            fontSize = 20.sp
                        )
                       DeletionRow(isInSelectionMode, turnOnSelectionMode = {viewModel.onEvent(AllTasksEvents.TurnOnSelectionModeForDelete)
                       }, deleteChosenItems = {viewModel.deleteSelectedNotes() })


                   }
                    Spacer(modifier = Modifier.height(10.dp))
                    LazyVerticalGrid(
                        modifier = Modifier.clip(MaterialTheme.shapes.large),
                        columns = GridCells.Fixed(2),
                        verticalArrangement = Arrangement.spacedBy(20.dp),
                        horizontalArrangement = Arrangement.spacedBy(20.dp)
                    ) {
                        if (listOfNotes != null) {
                            items(listOfNotes) { task ->
                                TaskCard(
                                    tittle = task.tittle,
                                    description = task.description,

                                    goToTask = {
                                        viewModel.onEvent(
                                            AllTasksEvents.NavigateToCreatingTaskScreen(
                                                task.id
                                            )
                                        )
                                    },
                                    task = task,
                                    isInSelectionMode = isInSelectionMode,
                                    onSelectTask = { selectedNote, isSelected ->
                                        if (isSelected) {
                                            viewModel.addTaskToDelete(selectedNote)
                                        } else {
                                            viewModel.removeTaskFromDelete(selectedNote)
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

