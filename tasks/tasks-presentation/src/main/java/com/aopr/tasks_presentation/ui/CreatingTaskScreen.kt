package com.aopr.tasks_presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.aopr.tasks_presentation.events.creating_task_events.CreatingTaskEvents
import com.aopr.tasks_presentation.ui.UiHandlers.CreatingTaskUiEventHandler
import com.aopr.tasks_presentation.viewModels.CreatingTaskViewModel
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreatingTaskScreen() {
    CreatingTaskUiEventHandler()
    val viewModel = koinViewModel<CreatingTaskViewModel>()
    val heightScreen = LocalConfiguration.current.screenHeightDp
    val tittle by viewModel.tittleOfTask.collectAsState()
    val description by viewModel.descriptionOfTask.collectAsState()
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
                                    imageVector = Icons.Default.KeyboardArrowLeft,
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
                title = { /*TODO*/ })
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
                LazyColumn(
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth(0.95f)
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
                                value = tittle,
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
                                    value = description,
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
                                    Text(text = "fdgd")
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
                                    Text(text = "time")
                                }
                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth(0.9f)
                                        .fillMaxHeight(0.6f)
                                ) {
                                    Text(text = "dgf")
                                }
                            }
                        }
                    }
                    item {
                        Row(
                            modifier = Modifier
                                .height((heightScreen * 0.05).dp)
                                .fillMaxWidth(), horizontalArrangement = Arrangement.End
                        ) {
                            IconButton(  onClick = { viewModel.onEvent(CreatingTaskEvents.AddTextFieldForSubTask) },
                        ) {
                                Icon(imageVector = Icons.Default.Add, contentDescription = "")
                            }
                        }
                    }
                    itemsIndexed(viewModel.listOfSubTasks) { index, subTask ->
                        SubTaskCard(
                            modifier = Modifier.padding(8.dp),
                            tittle = subTask.description,
                            onValueChange = { newDescription ->
                                viewModel.onEvent(CreatingTaskEvents.UpdateTempSubTaskDescription(index, newDescription))
                            },
                            onCheckedChange = { isChecked ->
                                viewModel.onEvent(CreatingTaskEvents.UpdateTempSubTaskIsDone(index, isChecked))
                            },
                            isCompleted = subTask.isCompleted
                        )
                    }

                }

            }
        }


    }

}

@Composable
fun SubTaskCard(
    modifier: Modifier,
    tittle: String,
    onValueChange: (String) -> Unit,
    onCheckedChange: (Boolean) -> Unit,
    isCompleted: Boolean,
) {

    Card(modifier = modifier) {
        Column {
            TextField(value = tittle, onValueChange = onValueChange)
            Checkbox(checked = isCompleted, onCheckedChange = onCheckedChange)
        }
    }


}