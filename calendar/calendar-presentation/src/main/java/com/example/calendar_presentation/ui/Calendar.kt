package com.example.calendar_presentation.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.aopr.tasks_presentation.ui.ui_elements.CustomCalendar
import com.example.calendar_presentation.view_model.CalendarViewModel
import com.example.calendar_presentation.view_model.events.CalendarEvents
import com.example.calendar_presentation.view_model.ui_event_handler.CalendarUiEventHandler
import org.koin.androidx.compose.koinViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarScreen() {
    val calendarViewModel = koinViewModel<CalendarViewModel>()
    val listOfDates = calendarViewModel.datesWithTasks
    CalendarUiEventHandler()
    BackHandler {  }
    Scaffold(

        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    calendarViewModel.onEvent(
                        CalendarEvents.NavigateToCreateNewTaskOrExhistingTask(
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
                        onClick = { calendarViewModel.onEvent(CalendarEvents.NavigateBack) },
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
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {

            Box(modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.9f)) {
                CustomCalendar(
                    getTasksByDate = { calendarViewModel.onEvent(CalendarEvents.GetTasksByDate(it)) },
                    listOfDatesWithTask = listOfDates,
                    listOfTasks = calendarViewModel.tasksByDate,
                    navigateToTask = {
                        calendarViewModel.onEvent(
                            CalendarEvents.NavigateToCreateNewTaskOrExhistingTask(
                                it
                            )
                        )
                    }, isCreatingTaskScreen = false
                )
            }

        }

    }
}
