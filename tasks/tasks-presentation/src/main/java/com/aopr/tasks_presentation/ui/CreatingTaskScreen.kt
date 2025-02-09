package com.aopr.tasks_presentation.ui

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxColors
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
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.aopr.notes_presentation.R
import com.aopr.shared_ui.cardsView.CircularCheckbox
import com.aopr.shared_ui.cardsView.background
import com.aopr.shared_ui.infoBar.CustomInfoBar
import com.aopr.tasks_domain.models.ImportanceOfTask
import com.aopr.tasks_presentation.events.creating_task_events.CreatingTaskEvents
import com.aopr.tasks_presentation.ui.ui_elements.BottomSheetForCalendar
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
    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val heightScreen = LocalConfiguration.current.screenHeightDp

    BackHandler {

    }
    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent),
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
                        .fillMaxWidth(),
                ) {


                    item {
                        Spacer(modifier = Modifier.height(45.dp))

                        Column(
                            modifier = Modifier
                                .height((heightScreen * 0.15).dp)
                                .fillMaxWidth(),
                            verticalArrangement = Arrangement.spacedBy(20.dp)
                        ) {
                            Text(
                                text = stringResource(
                                    id = com.aopr.shared_ui.R.string.tittle
                                )
                            )
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
                                    unfocusedBorderColor = Color.Transparent
                                ),

                                )
                        }

                    }
                    item {
                        Column(
                            modifier = Modifier
                                .height((heightScreen * 0.28f).dp)
                                .fillMaxSize(), verticalArrangement = Arrangement.spacedBy(20.dp)
                        ) {
                            Text(text = stringResource(id = com.aopr.shared_ui.R.string.description))

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth(),
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
                                        unfocusedBorderColor = Color.Transparent
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
                    }
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height((heightScreen * 0.25).dp),
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize(),
                                verticalArrangement = Arrangement.spacedBy(20.dp)
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    horizontalArrangement = Arrangement.Start,
                                ) {
                                    Text(text = stringResource(id = R.string.selectDateAndTimeForReminder))
                                }
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .fillMaxHeight(0.9f),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {

                                    Column(
                                        modifier = Modifier
                                            .fillMaxHeight()
                                            .fillMaxWidth(0.5f),
                                        horizontalAlignment = Alignment.Start,
                                        verticalArrangement = Arrangement.SpaceEvenly
                                    ) {

                                        Card(
                                            modifier = Modifier
                                                .fillMaxWidth(0.9f)
                                                .fillMaxHeight(0.7f),
                                            colors = CardDefaults.cardColors(
                                                containerColor = Color.LightGray.copy(alpha = 0.2f)
                                            )
                                        ) {
                                            Column(
                                                modifier = Modifier.fillMaxSize(),
                                                verticalArrangement = Arrangement.SpaceEvenly,
                                                horizontalAlignment = Alignment.CenterHorizontally
                                            ) {
                                                Row(
                                                    modifier = Modifier.fillMaxWidth(0.95f),
                                                    horizontalArrangement = Arrangement.SpaceBetween
                                                ) {
                                                    IconButton(onClick = {
                                                        viewModel.onEvent(CreatingTaskEvents.ShowCalendarForReminder)
                                                    }) {
                                                        Icon(
                                                            imageVector = Icons.Default.DateRange,
                                                            contentDescription = ""
                                                        )
                                                    }
                                                    TextButton(onClick = {
                                                        viewModel.onEvent(CreatingTaskEvents.CleanDateOfTaskReminder)
                                                    }) {
                                                        Text(
                                                            text = stringResource(id = R.string.cleanField),
                                                            color = Color.White
                                                        )
                                                    }

                                                }
                                                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
                                                Text(
                                                    text = dateOfTaskForReminder?.format(
                                                        DateTimeFormatter.ofPattern(
                                                            "dd MMM yyyy"
                                                        )
                                                    )
                                                        ?: "",
                                                )
                                            }
                                            }
                                        }
                                    }
                                    Column(
                                        modifier = Modifier
                                            .fillMaxHeight()
                                            .fillMaxWidth(),
                                        verticalArrangement = Arrangement.Center,
                                        horizontalAlignment = Alignment.End
                                    ) {

                                        Card(
                                            modifier = Modifier
                                                .fillMaxWidth(0.9f)
                                                .fillMaxHeight(0.7f),
                                            colors = CardDefaults.cardColors(
                                                containerColor = Color.LightGray.copy(alpha = 0.2f)
                                            )
                                        ) {
                                            Box(modifier = Modifier.fillMaxSize()) {
                                                Column(
                                                    modifier = Modifier.fillMaxSize(),
                                                    verticalArrangement = Arrangement.SpaceEvenly,
                                                    horizontalAlignment = Alignment.CenterHorizontally
                                                ) {
                                                    Row(
                                                        modifier = Modifier.fillMaxWidth(0.95f),
                                                        horizontalArrangement = Arrangement.SpaceBetween
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
                                                        TextButton(onClick = {
                                                            viewModel.onEvent(CreatingTaskEvents.CleanTimeOfTaskReminder)
                                                        }) {
                                                            Text(
                                                                text = stringResource(id = R.string.cleanField),
                                                                color = Color.White
                                                            )
                                                        }
                                                    }

                                                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {

                                                        Text(
                                                            text = timeOfTask?.format(
                                                                DateTimeFormatter.ofPattern(
                                                                    "HH:mm"
                                                                )
                                                            )
                                                                ?: "",
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
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height((heightScreen * 0.25).dp),
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize(),
                                verticalArrangement = Arrangement.spacedBy(20.dp)
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    horizontalArrangement = Arrangement.Start,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(text = stringResource(id = R.string.selectDateForTaskToBeDone))
                                    Spacer(modifier = Modifier.width(10.dp))
                                    Icon(
                                        painter = painterResource(id = R.drawable.should_be_filled_icon),
                                        contentDescription = "should_be_filled_icon",
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .fillMaxHeight(0.9f),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {

                                    Column(
                                        modifier = Modifier
                                            .fillMaxHeight()
                                            .fillMaxWidth(0.5f),
                                        horizontalAlignment = Alignment.Start,
                                        verticalArrangement = Arrangement.SpaceEvenly
                                    ) {

                                        Card(
                                            modifier = Modifier
                                                .fillMaxWidth(0.9f)
                                                .fillMaxHeight(0.7f),
                                            colors = CardDefaults.cardColors(
                                                containerColor = Color.LightGray.copy(alpha = 0.2f)
                                            )
                                        ) {
                                            Column(
                                                modifier = Modifier.fillMaxSize(),
                                                verticalArrangement = Arrangement.SpaceEvenly,
                                                horizontalAlignment = Alignment.CenterHorizontally
                                            ) {
                                                Row(
                                                    modifier = Modifier.fillMaxWidth(0.95f),
                                                    horizontalArrangement = Arrangement.SpaceBetween
                                                ) {
                                                    IconButton(onClick = {
                                                        viewModel.onEvent(CreatingTaskEvents.ShowCalendarForToBeDone)
                                                    }) {
                                                        Icon(
                                                            imageVector = Icons.Default.DateRange,
                                                            contentDescription = ""
                                                        )
                                                    }

                                                }
                                                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {

                                                    Text(
                                                        text = dateOfTaskToBeDone?.format(
                                                            DateTimeFormatter.ofPattern(
                                                                "dd MMM yyyy"
                                                            )
                                                        )
                                                            ?: "",
                                                    )
                                                }
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
                                .height((heightScreen * 0.15).dp)
                                .fillMaxWidth(0.2f), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = stringResource(id = R.string.doneOrNo))
                            CircularCheckbox(checked = isTaskDone, onCheckedChange =  { isChecked ->
                                viewModel.onEvent(CreatingTaskEvents.UpdateIsDoneOfTask(isChecked))
                            })
                        }
                    }
                    item {
                        Row(
                            modifier = Modifier
                                .height((heightScreen * 0.2).dp)
                                .fillMaxWidth(), horizontalArrangement = Arrangement.Center
                        ) {
                            Column(
                                modifier = Modifier.fillMaxSize(),
                                verticalArrangement = Arrangement.spacedBy(30.dp)
                            ) {
                                Text(text = stringResource(id = R.string.choosePriority))
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
                                Icon(imageVector = Icons.Default.Add, contentDescription = "", modifier = Modifier.size(20.dp))
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
        viewModel.infoBar.value?.let {CustomInfoBar(message = it) }
    }
}
