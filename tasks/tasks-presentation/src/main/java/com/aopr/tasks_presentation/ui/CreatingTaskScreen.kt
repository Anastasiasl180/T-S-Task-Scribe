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
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
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
import com.aopr.tasks_domain.models.ImportanceOfTask
import com.aopr.tasks_presentation.events.creating_task_events.CreatingTaskEvents
import com.aopr.tasks_presentation.ui.UiHandlers.CreatingTaskUiEventHandler
import com.aopr.tasks_presentation.ui.ui_elements.ClockPicker
import com.aopr.tasks_presentation.ui.ui_elements.CustomCalendar
import com.aopr.tasks_presentation.ui.ui_elements.SegmentedDemo
import com.aopr.tasks_presentation.ui.ui_elements.SubTaskCard
import com.aopr.tasks_presentation.viewModels.CreatingTaskViewModel
import org.koin.androidx.compose.koinViewModel
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreatingTaskScreen() {

    CreatingTaskUiEventHandler()

    val viewModel = koinViewModel<CreatingTaskViewModel>()
    val dateOfTask by viewModel.dataOfTask
    val isCalendarVisible by viewModel.isCalendarVisible.collectAsState()
    val isClockVisible by viewModel.isClockVisible.collectAsState()
    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val timeOfTask by viewModel.timeOfTask
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
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Row(
                            modifier = Modifier.fillMaxWidth(0.95f),
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
                                Text(text = stringResource(id = com.aopr.shared_ui.R.string.PlusOnButton))
                            }

                        }
                    }
                },
                title = {  })
        }
    ) { paddingValues ->

        Box(
            modifier = Modifier
                .padding(
                    start = paddingValues.calculateStartPadding(LayoutDirection.Ltr),
                    top = paddingValues.calculateTopPadding(),
                    end = paddingValues.calculateEndPadding(LayoutDirection.Ltr),
                )
                .background(Color.DarkGray)
        ) {

            Box(
                modifier = Modifier
                    .fillMaxSize(), contentAlignment = Alignment.Center
            ) {

                if (isClockVisible) {
                    ModalBottomSheet(
                        onDismissRequest = { viewModel.onEvent(CreatingTaskEvents.HideClock) },
                        sheetState = bottomSheetState,

                    ) {  Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height((heightScreen * 0.9f).dp)
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.fillMaxWidth()
                        ) {

                            var selectedTime by remember {
                                mutableStateOf(timeOfTask ?: LocalTime.now())
                            }

                            ClockPicker(
                                initialTime = selectedTime,
                                onTimeChanged = { newTime ->
                                    // Update the local state variable
                                    selectedTime = newTime
                                }
                            )

                            Text(
                                text = selectedTime.format(DateTimeFormatter.ofPattern("HH:mm")),
                                modifier = Modifier.padding(top = 16.dp)
                            )

                            Button(onClick = {
                                viewModel.onEvent(CreatingTaskEvents.UpdateTimeOfTask(selectedTime))
                                viewModel.onEvent(CreatingTaskEvents.HideClock)
                            }) {
                                Text("Save")
                            }
                        }
                    }
                    }
                }}

        if (isCalendarVisible) {
            ModalBottomSheet(
                onDismissRequest = {
                    viewModel.onEvent(CreatingTaskEvents.HideCalendar)
                },
                sheetState = bottomSheetState
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height((heightScreen * 0.9f).dp)
                ) {
                    CustomCalendar(
                        initialSelectedDate = viewModel.dataOfTask.value,
                        onDateSelected = { selectedDate ->
                            viewModel.onEvent(CreatingTaskEvents.UpdateDateOfTask(selectedDate))
                        },
                        onDismiss = {
                            viewModel.onEvent(CreatingTaskEvents.HideCalendar)
                        }, listOfDates = listOfDatesWithTasks, getTasks = {
                            viewModel.onEvent(CreatingTaskEvents.GetTasksByDate(it))
                        }, listOfTasks = viewModel.tasksByDate
                    )
                }
            }
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(0.95f), contentPadding = paddingValues
        ) {

            item {
                Column(
                    modifier = Modifier
                        .height((heightScreen * 0.15).dp)
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.SpaceEvenly
                ) {
                    Text(text = stringResource(id = com.aopr.shared_domain.R.string.tittle))
                    TextField(
                        shape = MaterialTheme.shapes.medium,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(60.dp),
                        placeholder = {
                            Text(text = stringResource(id = com.aopr.shared_domain.R.string.tittle))
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
                    Text(text = stringResource(id = com.aopr.shared_domain.R.string.description))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {

                        TextField(
                            value = descriptionOfTask,
                            shape = MaterialTheme.shapes.medium,
                            placeholder = {
                                Text(text = stringResource(id = com.aopr.shared_domain.R.string.description))
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
                            IconButton(onClick = { viewModel.onEvent(CreatingTaskEvents.ShowCalendar)
                           }) {
                                Icon(imageVector = Icons.Default.DateRange, contentDescription = "")
                            }
                            Text(
                                text = dateOfTask?.format(DateTimeFormatter.ofPattern("dd MMM yyyy"))
                                    ?: "Select Date",
                            )
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
                            IconButton(onClick = { viewModel.onEvent(CreatingTaskEvents.ShowClock) }) {
                                Icon(
                                    imageVector = Icons.Default.Notifications,
                                    contentDescription = ""
                                )
                            }
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
                Row(
                    modifier = Modifier
                        .height((heightScreen * 0.1).dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "Subtasks:")
                    Text(text = viewModel.listOfSubTasks.size.toString())
                }
            }
            itemsIndexed(viewModel.listOfSubTasks) { index, subTask ->
                SubTaskCard(
                    modifier = Modifier.height((heightScreen * 0.12).dp),
                    tittle = subTask.description,
                    onValueChange = { newDescription ->
                        viewModel.onEvent(
                            CreatingTaskEvents.UpdateTempSubTaskDescription(
                                index,
                                newDescription
                            )
                        )
                    },
                    onCheckedChange = { isChecked ->
                        viewModel.onEvent(
                            CreatingTaskEvents.UpdateTempSubTaskIsDone(
                                index,
                                isChecked
                            )
                        )
                    },
                    isCompleted = subTask.isCompleted
                )
            }

        }

    }
}}
