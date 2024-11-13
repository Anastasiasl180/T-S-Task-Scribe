package com.aopr.tasks_presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aopr.tasks_presentation.events.creating_task_events.CreatingTaskEvents
import com.aopr.tasks_presentation.ui.UiHandlers.CreatingTaskUiEventHandler
import com.aopr.tasks_presentation.viewModels.CreatingTaskViewModel
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreatingTaskScreen() {
    CreatingTaskUiEventHandler()
    val viewModel = koinViewModel<CreatingTaskViewModel>()
    val tittle by viewModel.tittleOfTask.collectAsState()
    val description by viewModel.descriptionOfTask.collectAsState()
    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.DarkGray),
                navigationIcon = {
                    IconButton(
                        onClick = { viewModel.onEvent(CreatingTaskEvents.NavigateToBack)},
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
                }, actions = {

                    Spacer(modifier = Modifier.width(10.dp))
                    IconButton(
                        onClick = {viewModel.onEvent(CreatingTaskEvents.SaveTask) },
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(Color.White)
                            .size(50.dp)
                    ) {
                        Text(text = stringResource(id = com.aopr.shared_ui.R.string.PlusOnButton))
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
                .background(Color.DarkGray), contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.15f),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextField(
                        modifier = Modifier
                            .fillMaxSize(),
                        placeholder = {
                            Text(
                                text = stringResource(id = com.aopr.shared_domain.R.string.tittle),
                                fontSize = 35.sp,
                                color = Color.White
                            )
                        },
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            disabledContainerColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent
                        ),
                        value = tittle,
                        onValueChange = { tittle ->
                            viewModel.onEvent(
                                CreatingTaskEvents.UpdateTittle
                                    (tittle)
                            )
                        },
                        textStyle = TextStyle(
                            fontSize = 35.sp
                        ),
                    )
                }
                TextField(
                    modifier = Modifier.fillMaxSize(),
                    value = description,
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        disabledContainerColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent
                    ),
                    placeholder = {
                        Text(
                            text = stringResource(id = com.aopr.shared_domain.R.string.description),
                            color = Color.White, fontSize = 20.sp
                        )
                    },
                    onValueChange = { description ->
                        viewModel.onEvent(CreatingTaskEvents.UpdateDescription(description))
                    },
                    textStyle = TextStyle(
                        fontSize = 20.sp
                    )
                )

            }

        }


    }

}