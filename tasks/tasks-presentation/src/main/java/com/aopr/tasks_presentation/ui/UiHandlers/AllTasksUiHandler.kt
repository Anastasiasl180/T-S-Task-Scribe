package com.aopr.tasks_presentation.ui.UiHandlers

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import com.aopr.shared_ui.util.LocalNavigator
import com.aopr.shared_ui.util.currentOrThrow
import com.aopr.tasks_presentation.events.all_tasks_events.AllTasksUiEvents
import com.aopr.tasks_presentation.navigation.AllTasksNavRoutes
import com.aopr.tasks_presentation.viewModels.AllTasksViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun UiEventHandler() {
    val navigator = LocalNavigator.currentOrThrow()
    val viewModel = koinViewModel<AllTasksViewModel>()

    LaunchedEffect(Unit) {
        viewModel.events.collect {event->
            when(event){
                is AllTasksUiEvents.NavigateToCreateTaskScreen -> {
                    navigator.navigate(AllTasksNavRoutes.CreatingTaskScreen(event.id))
                }
            }
        }
    }

}