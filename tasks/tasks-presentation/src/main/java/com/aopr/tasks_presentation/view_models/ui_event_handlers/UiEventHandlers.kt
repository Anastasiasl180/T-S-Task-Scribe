package com.aopr.tasks_presentation.view_models.ui_event_handlers

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.toRoute
import com.aopr.shared_ui.navigation.LocalNavigator
import com.aopr.shared_ui.navigation.currentOrThrow
import com.aopr.tasks_presentation.view_models.events.all_tasks_events.AllTasksUiEvents
import com.aopr.tasks_presentation.view_models.events.creating_task_events.CreatingTaskEvents
import com.aopr.tasks_presentation.view_models.events.creating_task_events.CreatingTaskUiEvents
import com.aopr.tasks_presentation.navigation.AllTasksNavRoutes
import com.aopr.tasks_presentation.view_models.AllTasksViewModel
import com.aopr.tasks_presentation.view_models.CreatingTaskViewModel
import org.koin.androidx.compose.koinViewModel
@Composable
fun AllTasksUiEventHandler() {
    val navigator = LocalNavigator.currentOrThrow()
    val viewModel = koinViewModel<AllTasksViewModel>()

    LaunchedEffect(Unit) {
        viewModel.events.collect {event->
            when(event){
                is AllTasksUiEvents.NavigateToCreateTaskScreen -> {
                    navigator.navigate(AllTasksNavRoutes.CreatingTaskScreen(event.id))
                }
                AllTasksUiEvents.NavigateBack -> {
                    navigator.popBackStack()
                }
            }
        }
    }

}
@Composable
fun CreatingTaskUiEventHandler() {

    val navigator = LocalNavigator.currentOrThrow()
    val viewModel = koinViewModel<CreatingTaskViewModel>()
    val route = navigator.currentBackStackEntry?.toRoute<AllTasksNavRoutes.CreatingTaskScreen>()
    val id = route?.id

    LaunchedEffect(Unit) {
        viewModel.uiEvents.collect { event ->
            when (event) {
                CreatingTaskUiEvents.NavigateToBack -> {
                    navigator.popBackStack()
                }
            }
        }
    }
    LaunchedEffect(Unit) {
        if (id != null) {
            viewModel.onEvent(CreatingTaskEvents.GetTakById(id))
        }
    }

}

