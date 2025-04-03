package com.aopr.tasks_presentation.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aopr.notes_presentation.R
import com.aopr.shared_ui.cardsView.CircularCheckbox
import com.aopr.shared_ui.cardsView.background
import com.aopr.shared_ui.infoBar.CustomInfoBar
import com.aopr.shared_ui.top_app_bar.searchBarScrollBehaviour
import com.aopr.tasks_domain.models.ImportanceOfTask
import com.aopr.tasks_presentation.view_models.events.creating_task_events.CreatingTaskEvents
import com.aopr.tasks_presentation.ui.ui_elements.CustomCalendar
import com.aopr.tasks_presentation.ui.ui_elements.BottomSheetForClock
import com.aopr.tasks_presentation.ui.ui_elements.SegmentedDemo
import com.aopr.tasks_presentation.ui.ui_elements.SubTasksList
import com.aopr.tasks_presentation.view_models.CreatingTaskViewModel
import com.aopr.tasks_presentation.view_models.ui_event_handlers.CreatingTaskUiEventHandler
import org.koin.androidx.compose.koinViewModel
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreatingTaskScreen() {

    CreatingTaskUiEventHandler()
    val topAppBarDefaults = searchBarScrollBehaviour()
    val viewModel = koinViewModel<CreatingTaskViewModel>()
    val dateOfTaskForReminder by viewModel.dataOfTaskForReminder
    val dateOfTaskToBeDone by viewModel.dataOfTaskToBeDone
    val isCalendarVisible by viewModel.isCalendarVisible.collectAsState()
    val isClockVisible by viewModel.isClockVisible.collectAsState()
    val timeOfTask by viewModel.timeOfTask
    val timeOfSubTask by viewModel.timeOfSubTask
    val dateOfSubTask by viewModel.dateOfSubTask
    val isTaskDone by viewModel.isDoneTask.collectAsState()
    val priorityOfTask by viewModel.priority.collectAsState()
    val tittleOfTask by viewModel.tittleOfTask.collectAsState()
    val descriptionOfTask by viewModel.descriptionOfTask.collectAsState()
    val listOfDatesWithTasks = viewModel.datesWithTasks
    val importanceItems = ImportanceOfTask.entries
    val backgroundTheme = background()
    val isDoneButtonForTaskShowed = viewModel.isCompletedButtonShowed.collectAsState()
    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val heightScreen = LocalConfiguration.current.screenHeightDp

    BackHandler {

    }

    Scaffold(modifier = Modifier.nestedScroll(topAppBarDefaults.nestedScrollConnection),
        topBar = {
            TopAppBar(colors = TopAppBarDefaults.mediumTopAppBarColors(
                containerColor = Color.Transparent,
                scrolledContainerColor = Color.Transparent
            ), scrollBehavior = topAppBarDefaults,
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
                                    .background(Color.DarkGray.copy(alpha = 0.6f))
                                    .size(50.dp)
                            ) {

                                Icon(
                                    imageVector = Icons.AutoMirrored.Default.KeyboardArrowLeft,
                                    contentDescription = "",
                                    tint = Color.White, modifier = Modifier.size(20.dp)
                                )
                            }
                            IconButton(
                                onClick = { viewModel.onEvent(CreatingTaskEvents.SaveTask) },
                                modifier = Modifier
                                    .clip(CircleShape)
                                    .background(Color.DarkGray.copy(alpha = 0.6f))
                                    .size(50.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Add,
                                    contentDescription = "",
                                    tint = Color.White, modifier = Modifier.size(20.dp)
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
                .background(backgroundTheme),
            contentAlignment = Alignment.BottomCenter
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight(0.9f)
                    .fillMaxWidth(0.9f), contentAlignment = Alignment.Center
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
                    CustomCalendar(
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
                        listOfDatesWithTask = listOfDatesWithTasks, navigateToTask = {
                            viewModel.onEvent(CreatingTaskEvents.GetTakById(it))
                        }, isCreatingTaskScreen = true
                    )

                }
                LazyColumn(
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth(),
                ) {


                    item {
                        Spacer(modifier = Modifier.height(35.dp))

                        Row(
                            modifier = Modifier
                                .height((heightScreen * 0.1).dp)
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {

                            OutlinedTextField(
                                shape = MaterialTheme.shapes.medium,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(60.dp)
                                    .border(
                                        width = 0.5.dp,
                                        color = Color.Transparent,
                                        shape = MaterialTheme.shapes.medium
                                    ),
                                placeholder = {
                                    Text(text = stringResource(id = com.aopr.shared_ui.R.string.tittle))
                                },
                                value = tittleOfTask,
                                onValueChange = {
                                    viewModel.onEvent(
                                        CreatingTaskEvents.UpdateTittleOfTask(
                                            it
                                        )
                                    )
                                },
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedContainerColor = Color.LightGray.copy(alpha = 0.2f),
                                    unfocusedContainerColor = Color.LightGray.copy(alpha = 0.2f),
                                    focusedBorderColor = Color.White.copy(alpha = 0.5f),
                                    unfocusedBorderColor = Color.Transparent,cursorColor = Color.White
                                ),

                                )
                        }

                    }



                    item {

                        Row(
                            modifier = Modifier
                                .height((heightScreen * 0.28f).dp)
                                .fillMaxSize(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {

                            OutlinedTextField(
                                value = descriptionOfTask,
                                shape = MaterialTheme.shapes.medium,
                                placeholder = {
                                    Text(text = stringResource(id = com.aopr.shared_ui.R.string.description))
                                },
                                onValueChange = {
                                    viewModel.onEvent(
                                        CreatingTaskEvents.UpdateDescriptionOfTask(
                                            it
                                        )
                                    )
                                },

                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedContainerColor = Color.LightGray.copy(alpha = 0.2f),
                                    unfocusedContainerColor = Color.LightGray.copy(alpha = 0.2f),
                                    focusedBorderColor = Color.White.copy(alpha = 0.5f),
                                    unfocusedBorderColor = Color.Transparent, cursorColor = Color.White
                                ),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(170.dp)
                                    .border(
                                        width = 0.5.dp,
                                        color = Color.Transparent,
                                        shape = MaterialTheme.shapes.medium
                                    ),
                                maxLines = 20,
                            )

                        }

                    }
                    item {
                        if (isDoneButtonForTaskShowed.value) {
                            Row(
                                modifier = Modifier
                                    .height((heightScreen * 0.1).dp)
                                    .fillMaxWidth(0.5f),
                                horizontalArrangement = Arrangement.spacedBy(15.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(text = stringResource(id = R.string.doneOrNo), fontSize = 25.sp)
                                CircularCheckbox(
                                    checked = isTaskDone,
                                    onCheckedChange = { isChecked ->
                                        viewModel.onEvent(
                                            CreatingTaskEvents.UpdateIsDoneOfTask(
                                                isChecked
                                            )
                                        )
                                    })
                            }
                        }
                    }
                    item {

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height((heightScreen * 0.1).dp),
                            horizontalArrangement = Arrangement.Start,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = "Reminder", fontSize = 35.sp)

                        }


                    }

                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height((heightScreen * 0.12).dp),
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxSize(),
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ) {

                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .fillMaxHeight(0.8f),
                                    colors = CardDefaults.cardColors(
                                        containerColor = Color.LightGray.copy(alpha = 0.2f)
                                    )
                                ) {
                                    Box(
                                        modifier = Modifier.fillMaxSize(),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Row(
                                            modifier = Modifier
                                                .fillMaxWidth(0.9f)
                                                .fillMaxHeight(),
                                            horizontalArrangement = Arrangement.SpaceBetween,
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            IconButton(onClick = {
                                                viewModel.onEvent(CreatingTaskEvents.ShowCalendarForReminder)
                                            }) {
                                                Icon(
                                                    imageVector = Icons.Default.DateRange,
                                                    contentDescription = ""
                                                )
                                            }

                                            Text(
                                                text = dateOfTaskForReminder?.format(
                                                    DateTimeFormatter.ofPattern(
                                                        "dd MMM yyyy"
                                                    )
                                                )
                                                    ?: stringResource(id = R.string.selectDateReminder)
                                            )

                                            TextButton(onClick = {
                                                viewModel.onEvent(CreatingTaskEvents.CleanDateOfTaskReminder)
                                            }) {
                                                Text(
                                                    text = stringResource(id = R.string.cleanField),
                                                    color = Color.White
                                                )
                                            }

                                        }
                                    }


                                }
                            }

                        }
                    }


                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height((heightScreen * 0.12).dp),
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxSize(),
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .fillMaxHeight(0.8f),
                                    colors = CardDefaults.cardColors(
                                        containerColor = Color.LightGray.copy(alpha = 0.2f)
                                    )
                                ) {
                                    Box(
                                        modifier = Modifier.fillMaxSize(),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Row(
                                            modifier = Modifier
                                                .fillMaxWidth(0.9f)
                                                .fillMaxHeight(),
                                            horizontalArrangement = Arrangement.SpaceBetween,
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {

                                            IconButton(onClick = {
                                                viewModel.onEvent(
                                                    CreatingTaskEvents.ShowClockForTaskReminder
                                                )
                                            }) {
                                                Icon(
                                                    imageVector = Icons.Outlined.Notifications,
                                                    contentDescription = ""
                                                )
                                            }
                                            Text(
                                                text =
                                                timeOfTask?.format(
                                                    DateTimeFormatter.ofPattern(
                                                        "HH:mm"
                                                    )
                                                )
                                                    ?: stringResource(id = R.string.selectTimeForReminder)
                                            )
                                            TextButton(onClick = {
                                                viewModel.onEvent(CreatingTaskEvents.CleanTimeOfTaskReminder)
                                            }) {
                                                Text(
                                                    text = stringResource(id = R.string.cleanField),
                                                    color = Color.White
                                                )
                                            }


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
                                .height((heightScreen * 0.05).dp),
                            horizontalArrangement = Arrangement.Start,
                            verticalAlignment = Alignment.Bottom
                        ) {
                            Text(
                                text = stringResource(id = R.string.selectDateForTaskToBeDone),
                                fontSize = 15.sp
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Icon(
                                painter = painterResource(id = R.drawable.should_be_filled_icon),
                                contentDescription = "should_be_filled_icon",
                                modifier = Modifier.size(20.dp)
                            )
                        }


                    }

                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height((heightScreen * 0.12).dp),
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxSize(),
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .fillMaxHeight(0.8f),
                                    colors = CardDefaults.cardColors(
                                        containerColor = Color.LightGray.copy(alpha = 0.2f)
                                    )
                                ) {
                                    Box(
                                        modifier = Modifier.fillMaxSize(),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Row(
                                            modifier = Modifier
                                                .fillMaxWidth(0.9f)
                                                .fillMaxHeight(),
                                            horizontalArrangement = Arrangement.SpaceBetween,
                                            verticalAlignment = Alignment.CenterVertically
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
                                                text =
                                                dateOfTaskToBeDone?.format(
                                                    DateTimeFormatter.ofPattern(
                                                        "dd MMM yyyy"
                                                    )
                                                )
                                                    ?: stringResource(id = R.string.selectTimeForReminder)
                                            )

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
                                .height((heightScreen * 0.05).dp),
                            horizontalArrangement = Arrangement.Start,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                                Text(text = stringResource(id = R.string.choosePriority),
                                fontSize = 15.sp
                            )
                        }
                    }
                    item {
                        Row(
                            modifier = Modifier
                                .height((heightScreen * 0.15).dp)
                                .fillMaxWidth(), horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically
                        ) {

                                SegmentedDemo(
                                    items = importanceItems,
                                    selectedItem = priorityOfTask,
                                    heightOfTrack = 0.5f,
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
                    item {
                        Row(
                            modifier = Modifier
                                .height((heightScreen * 0.1).dp)
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.End,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = stringResource(id = R.string.addSubtask))
                            Spacer(modifier = Modifier.width(10.dp))
                            IconButton(
                                onClick = { viewModel.onEvent(CreatingTaskEvents.AddTextFieldForSubTask) },
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Add,
                                    contentDescription = "",
                                    modifier = Modifier.size(20.dp)
                                )
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
                                viewModel.onEvent(CreatingTaskEvents.CleanTimeOfSubtask(it))

                            }, clearDataOfSubTask = {
                                viewModel.onEvent(CreatingTaskEvents.CleanDataOfSubTask(it))
                            }
                        )
                    }

                }
            }
        }
        viewModel.infoBar.value?.let { CustomInfoBar(message = it) }
    }
}
