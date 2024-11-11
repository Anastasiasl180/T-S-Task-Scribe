package com.aopr.tasks_presentation.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.aopr.tasks_presentation.viewModels.CreatingTaskViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun CreatingTaskScreen() {
    val viewModel = koinViewModel<CreatingTaskViewModel>()

}