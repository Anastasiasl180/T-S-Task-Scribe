package com.example.calendar_presentation.view_model.ui_event_handler

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import com.aopr.shared_ui.navigation.LocalNavigator
import com.aopr.shared_ui.navigation.currentOrThrow
import com.aopr.tasks_presentation.navigation.AllTasksNavRoutes
import com.example.calendar_presentation.view_model.CalendarViewModel
import com.example.calendar_presentation.view_model.events.CalendarUiEvents
import kotlinx.coroutines.flow.collect
import org.koin.androidx.compose.koinViewModel

@Composable
fun CalendarUiEventHandler() {

    val navigator = LocalNavigator.currentOrThrow()
    val calendarViewModel = koinViewModel<CalendarViewModel>()

    LaunchedEffect(Unit) {
        calendarViewModel.event.collect() { event ->
            when (event) {
                CalendarUiEvents.NavigateBack -> {
                    navigator.popBackStack()
                }

               is CalendarUiEvents.NavigateToCreateTaskScreen -> {

                   if (event.id == null){
                       navigator.navigate(AllTasksNavRoutes.CreatingTaskScreen(null))
                   }else{
                       navigator.navigate(AllTasksNavRoutes.CreatingTaskScreen(event.id))
                   }

                }
            }

        }
    }
}