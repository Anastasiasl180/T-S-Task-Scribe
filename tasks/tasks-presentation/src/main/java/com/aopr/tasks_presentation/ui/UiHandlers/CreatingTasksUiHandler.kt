package com.aopr.tasks_presentation.ui.UiHandlers

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.toRoute
import com.aopr.shared_ui.util.LocalNavigator
import com.aopr.shared_ui.util.currentOrThrow
import com.aopr.tasks_presentation.events.creating_task_events.CreatingTaskEvents
import com.aopr.tasks_presentation.events.creating_task_events.CreatingTaskUiEvents
import com.aopr.tasks_presentation.navigation.AllTasksNavRoutes
import com.aopr.tasks_presentation.viewModels.CreatingTaskViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun CreatingTaskUiEventHandler() {

    val navigator = LocalNavigator.currentOrThrow()
    val viewModel = koinViewModel<CreatingTaskViewModel>()
    val id = navigator.currentBackStackEntry?.toRoute<AllTasksNavRoutes.CreatingTaskScreen>()
    val idd = id?.id

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
        if (idd != null) {
            Log.wtf("Meerka", "Merno1")
            Log.wtf("Meerka", idd.toString())
            viewModel.onEvent(CreatingTaskEvents.GetTakById(idd))
        }else{
            Log.wtf("Meerka"," Merno ")
        }
    }

}
