package com.aopr.tasks_presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.aopr.notes_presentation.R
import com.aopr.tasks_domain.models.ImportanceOfTask
import com.aopr.tasks_presentation.events.creating_task_events.CreatingTaskEvents
import com.aopr.tasks_presentation.ui.UiHandlers.CreatingTaskUiEventHandler
import com.aopr.tasks_presentation.ui.ui_elements.BottomSheetForCalendar
import com.aopr.tasks_presentation.ui.ui_elements.BottomSheetForClock
import com.aopr.tasks_presentation.ui.ui_elements.SegmentedDemo
import com.aopr.tasks_presentation.ui.ui_elements.SubTasksList
import com.aopr.tasks_presentation.view_models.CreatingTaskViewModel
import org.koin.androidx.compose.koinViewModel
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreatingTaskScreen() {

    CreatingTaskUiEventHandler()

    val viewModel = koinViewModel<CreatingTaskViewModel>()
    val dateOfTaskForReminder by viewModel.dataOfTaskForReminder
    val dateOfTaskToBeDone by viewModel.dataOfTaskToBeDone
    val isCalendarVisible by viewModel.isCalendarVisible.collectAsState()
    val isClockVisible by viewModel.isClockVisible.collectAsState()
    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val timeOfTask by viewModel.timeOfTask

    val timeOfSubTask by viewModel.timeOfSubTask
    val dateOfSubTask by viewModel.dateOfSubTask

    val priorityOfTask by viewModel.priority.collectAsState()
    val importanceItems = ImportanceOfTask.entries
    val heightScreen = LocalConfiguration.current.screenHeightDp
    val tittleOfTask by viewModel.tittleOfTask.collectAsState()
    val descriptionOfTask by viewModel.descriptionOfTask.collectAsState()
    val listOfDatesWithTasks = viewModel.datesWithTasks


    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.DarkGray),
                actions = {
                    Box(
                        modifier = Modifier
                            .fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(0.95f),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            IconButton(
                                onClick = { viewModel.onEvent(CreatingTaskEvents.NavigateToBack) },
                                modifier = Modifier
                                    .clip(CircleShape)
                                    .background(Color.White)
                                    .size(50.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                                    contentDescription = "",
                                    tint = Color.Black
                                )
                            }
                            IconButton(
                                onClick = { viewModel.onEvent(CreatingTaskEvents.SaveTask) },
                                modifier = Modifier
                                    .clip(CircleShape)
                                    .background(Color.White)
                                    .size(50.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Add,
                                    contentDescription = "AddFloatingActionButton",
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }
                    }
                },
                title = { }
            )
        }
    ) { paddingValues ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.DarkGray)
                .padding(
                    start = paddingValues.calculateStartPadding(LayoutDirection.Ltr),
                    top = paddingValues.calculateTopPadding(),
                    end = paddingValues.calculateEndPadding(LayoutDirection.Rtl),
                ),
            contentAlignment = Alignment.Center
        ) {

            if (isClockVisible) {

                BottomSheetForClock(
                    onDismissRequest = { viewModel.onEvent(CreatingTaskEvents.HideClock) },
                    sheetState = bottomSheetState,
                    clockMode = viewModel.clockMode.value,
                    timeOfTask = timeOfTask,
                    timeOfSubTask = timeOfSubTask,
                    updateTimeOfTask = {
                        viewModel.onEvent(
                            CreatingTaskEvents.UpdateTimeOfTask(it)
                        )
                    },
                    updateTimeOfSubTask = {
                        viewModel.onEvent(
                            CreatingTaskEvents.UpdateTimeForSubTask(
                               it
                            )
                        )
                    },
                    hideClock = {
                        viewModel.onEvent(CreatingTaskEvents.HideClock)

                    },
                    heightOfScreen = heightScreen
                )

            }

            if (isCalendarVisible) {

                BottomSheetForCalendar(
                    onDismiss = {
                        viewModel.onEvent(CreatingTaskEvents.HideCalendar)

                    },
                    sheetState = bottomSheetState,
                    initialSelectedDate = viewModel.dataOfTaskToBeDone.value,
                    calendarMode = viewModel.calendarMode.value,
                    updateDateOfTaskToBeDone = {
                        viewModel.onEvent(
                            CreatingTaskEvents.UpdateDateOfTaskToBeDone(it)
                        )
                    },
                    updateDateOfTaskForReminder = {
                        viewModel.onEvent(
                            CreatingTaskEvents.UpdateDateOfTaskForReminder(it)
                        )
                    },
                    updateDateForSubtask = {
                        viewModel.onEvent(
                            CreatingTaskEvents.UpdateDateForSubtask(it)
                        )
                    },
                    hideCalendar = {
                        viewModel.onEvent(CreatingTaskEvents.HideCalendar)

                    },
                    getTasksByDate = {
                        viewModel.onEvent(CreatingTaskEvents.GetTasksByDate(it))

                    },
                    heightScreen = heightScreen,
                    listOfTasks = viewModel.tasksByDate,
                    listOfDatesWithTask = listOfDatesWithTasks
                )

            }

            LazyColumn(
                modifier = Modifier
                    .fillMaxHeight()
                    .background(Color.DarkGray)
                    .fillMaxWidth(0.95f),
            ) {

                item {
                    Column(
                        modifier = Modifier
                            .height((heightScreen * 0.15).dp)
                            .fillMaxWidth(),
                        verticalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Text(text = stringResource(id = com.aopr.shared_ui.R.string.tittle
                        ))
                        TextField(
                            shape = MaterialTheme.shapes.medium,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(60.dp),
                            placeholder = {
                                Text(text = stringResource(id =com.aopr.shared_ui.R.string.tittle))
                            },
                            value = tittleOfTask,
                            onValueChange = {
                                viewModel.onEvent(
                                    CreatingTaskEvents.UpdateTittleOfTask(
                                        it
                                    )
                                )
                            },
                            colors = TextFieldDefaults.colors(
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent
                            ),

                            )
                    }

                }
                item {
                    Column(
                        modifier = Modifier
                            .height((heightScreen * 0.28f).dp)
                            .fillMaxSize(), verticalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Text(text = stringResource(id = com.aopr.shared_ui.R.string.description))

                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center
                        ) {

                            TextField(
                                value = descriptionOfTask,
                                shape = MaterialTheme.shapes.medium,
                                placeholder = {
                                    Text(text = stringResource(id = com.aopr.shared_ui.R.string.tittle))
                                },
                                onValueChange = {
                                    viewModel.onEvent(
                                        CreatingTaskEvents.UpdateDescriptionOfTask(
                                            it
                                        )
                                    )
                                },
                                colors = TextFieldDefaults.colors(
                                    focusedIndicatorColor = Color.Transparent,
                                    unfocusedIndicatorColor = Color.Transparent
                                ),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(170.dp),
                                maxLines = 20,
                            )

                        }
                    }
                }
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height((heightScreen * 0.2).dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxHeight()
                                .fillMaxWidth(0.5f),
                            horizontalAlignment = Alignment.Start,
                            verticalArrangement = Arrangement.SpaceEvenly
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.Start
                            ) {
                                Text(text = "date")
                            }
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth(0.9f)
                                    .fillMaxHeight(0.6f)
                            ) {
                                Row {


                                    IconButton(onClick = {
                                        viewModel.onEvent(CreatingTaskEvents.ShowCalendarForReminder)
                                    }) {
                                        Icon(
                                            imageVector = Icons.Default.DateRange,
                                            contentDescription = ""
                                        )
                                    }
                                    IconButton(onClick = {
                                        viewModel.onEvent(CreatingTaskEvents.CleanDateOfTaskReminder)
                                    }) {
                                        Icon(imageVector = Icons.Default.Delete,"")
                                    }
                                    Text(
                                        text = dateOfTaskForReminder?.format(
                                            DateTimeFormatter.ofPattern(
                                                "dd MMM yyyy"
                                            )
                                        )
                                            ?: "Select Date for reminder",
                                    )
                                }
                            }
                        }
                        Column(
                            modifier = Modifier
                                .fillMaxHeight()
                                .fillMaxWidth(),
                            verticalArrangement = Arrangement.SpaceEvenly,
                            horizontalAlignment = Alignment.End
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.Start
                            ) {
                                Text(
                                    text = timeOfTask?.format(DateTimeFormatter.ofPattern("HH:mm"))
                                        ?: "",
                                )
                            }
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth(0.9f)
                                    .fillMaxHeight(0.6f)
                            ) {
                                Row {

                                IconButton(onClick = { viewModel.onEvent(CreatingTaskEvents.ShowClockForTaskReminder) }) {
                                    Icon(
                                        imageVector = Icons.Default.Notifications,
                                        contentDescription = ""
                                    )
                                }
                                IconButton(onClick = {
                                    viewModel.onEvent(CreatingTaskEvents.CleanTimeOfTaskReminder)
                                }) {
                                    Icon(imageVector = Icons.Default.Delete, "")
                                }
                            }
                            }
                        }
                    }
                }
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height((heightScreen * 0.2).dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxHeight()
                                .fillMaxWidth(0.5f),
                            horizontalAlignment = Alignment.Start,
                            verticalArrangement = Arrangement.SpaceEvenly
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.Start
                            ) {
                                Text(text = "date")
                            }
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth(0.9f)
                                    .fillMaxHeight(0.6f)
                            ) {
                                IconButton(onClick = {
                                    viewModel.onEvent(CreatingTaskEvents.ShowCalendarForToBeDone)
                                }) {
                                    Icon(
                                        imageVector = Icons.Default.DateRange,
                                        contentDescription = ""
                                    )
                                }
                                Text(
                                    text = dateOfTaskToBeDone?.format(DateTimeFormatter.ofPattern("dd MMM yyyy"))
                                        ?: "Select Date to be done",
                                )
                            }
                        }
                    }
                }
                item {
                    Row(
                        modifier = Modifier
                            .height((heightScreen * 0.25).dp)
                            .fillMaxWidth(), horizontalArrangement = Arrangement.End
                    ) {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.SpaceEvenly
                        ) {
                            Text(text = "Priority")
                            SegmentedDemo(
                                items = importanceItems,
                                selectedItem = priorityOfTask, heightOfTrack = 0.4f,
                                onImportanceChange = { selectedPriority ->
                                    viewModel.onEvent(
                                        CreatingTaskEvents.UpdatePriorityOfTask(
                                            selectedPriority
                                        )
                                    )
                                }
                            )
                        }
                    }
                }
                item {
                    Row(
                        modifier = Modifier
                            .height((heightScreen * 0.1).dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "Add subtask")
                        Spacer(modifier = Modifier.width(10.dp))
                        IconButton(
                            onClick = { viewModel.onEvent(CreatingTaskEvents.AddTextFieldForSubTask) },
                        ) {
                            Icon(imageVector = Icons.Default.Add, contentDescription = "")
                        }
                    }
                }

                item {
                    SubTasksList(
                        subtasks = viewModel.listOfSubTasks,
                        onUpdateDescription = { index, description ->
                            viewModel.onEvent(
                                CreatingTaskEvents.UpdateTempSubTaskDescription(
                                    index,
                                    description
                                )
                            )
                        },
                        onUpdateIsCompleted = { index, isCompleted ->
                            viewModel.onEvent(
                                CreatingTaskEvents.UpdateTempSubTaskIsDone(
                                    index,
                                    isCompleted
                                )
                            )
                        },
                        onDeleteSubTask = { index ->
                            if (viewModel.existingTask.value != null) {
                                viewModel.onEvent(
                                    CreatingTaskEvents.RemoveTextFieldForSubTask(
                                        task = viewModel.existingTask.value, index
                                    )
                                )
                            } else {
                                viewModel.onEvent(
                                    CreatingTaskEvents.RemoveTextFieldForSubTask(
                                        task = viewModel.existingTask.value, index
                                    )
                                )
                            }


                        },
                        data = dateOfSubTask,
                        time = timeOfSubTask,
                        showCalendarForReminder = {
                            viewModel.onEvent(
                                CreatingTaskEvents.ShowCalendarForSubTask(
                                    it
                                )
                            )
                        },
                        showClockForReminder = {
                            viewModel.onEvent(
                                CreatingTaskEvents.ShowClockForSubTaskReminder(
                                    it
                                )
                            )
                        }, clearTimeOfSubTask = {
                            viewModel.onEvent(CreatingTaskEvents.CleanTimeOfSubtask)

                        }, clearDataOfSubTask = {
                            viewModel.onEvent(CreatingTaskEvents.CleanDataOfSubTask)
                        }
                    )
                }

            }

        }
    }
}
