package com.aopr.onboarding_presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.aopr.onboarding_presentation.R
import com.aopr.onboarding_presentation.view_models.events.loading_screen_events.LoadingEvents
import com.aopr.onboarding_presentation.view_models.LoadingViewModel
import com.aopr.onboarding_presentation.view_models.ui_events_handler.LoadingUiEventsHandler
import com.aopr.shared_ui.cardsView.background
import kotlinx.coroutines.delay
import org.koin.androidx.compose.koinViewModel

@Composable
fun LoadingScreen() {
    LoadingUiEventsHandler()
    val viewModel = koinViewModel<LoadingViewModel>()
    val background = background()
    LaunchedEffect(key1 = Unit) {
        delay(5000)
        viewModel.onEvent(LoadingEvents.NavigateToFirstOnBoardingScreenOrHomeScreen)
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(background), contentAlignment = Alignment.Center
    ) {
        Text(text = stringResource(R.string.Task_Scribe))
    }

}