package com.aopr.onboarding_presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.aopr.onboarding_presentation.events.loading_screen_events.LoadingEvents
import com.aopr.onboarding_presentation.model.LoadingViewModel
import com.aopr.onboarding_presentation.ui_events_handler.LoadingUiEventsHandler
import kotlinx.coroutines.delay
import org.koin.androidx.compose.koinViewModel

@Composable
fun LoadingScreen() {
    LoadingUiEventsHandler()
    val viewModel = koinViewModel<LoadingViewModel>()
    LaunchedEffect(key1 = Unit) {
        delay(5000)
        viewModel.onEvent(LoadingEvents.NavigateToFirstOnBoardingScreenOrHomeScreen)
    }
    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color.Red)) {

    }

}