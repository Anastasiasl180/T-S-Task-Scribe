package com.aopr.tasks_presentation.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.aopr.tasks_presentation.events.all_tasks_events.AllTasksEvents
import com.aopr.tasks_presentation.ui.UiHandlers.AllTasksUiEventHandler
import com.aopr.tasks_presentation.view_models.AllTasksViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun AllTasksScreen() {
    val viewModel = koinViewModel<AllTasksViewModel>()
    BackHandler {

    }
    AllTasksUiEventHandler()
    Scaffold(floatingActionButton = {
        FloatingActionButton(
            onClick = { viewModel.onEvent(AllTasksEvents.NavigateToCreatingTaskScreen(null)) },
            modifier = Modifier.clip(shape = MaterialTheme.shapes.extraLarge)
        ) {
            Text(text = stringResource(id = com.aopr.shared_domain.R.string.addForIcon))
        }
    }) { _paddingValues ->

        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Column {
                viewModel.listOfTasks.value?.forEach { task ->
                    Card(modifier = Modifier
                        .height(200.dp)
                        .clickable {
                            viewModel.onEvent(AllTasksEvents.NavigateToCreatingTaskScreen(task.id))
                        }) {
                        Row(modifier = Modifier.fillMaxWidth()) {
                            Text(text = task.id.toString())
                            Button(onClick = {
                                viewModel.onEvent(AllTasksEvents.DeleteTask(task))
                            }) {
                                Text(text = "delete")
                            }
                        }

                    }
                    }
                }
            }

        }
    }

