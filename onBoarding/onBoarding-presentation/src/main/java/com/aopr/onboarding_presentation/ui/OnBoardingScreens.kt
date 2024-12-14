package com.aopr.onboarding_presentation.ui

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.aopr.onboarding_presentation.events.first_screen_events.FirstScreenEvents
import com.aopr.onboarding_presentation.events.second_screen_events.SecondScreenEvents
import com.aopr.onboarding_presentation.model.FirstOnBoardingViewModel
import com.aopr.onboarding_presentation.model.SecondOnBoardingViewModel
import com.aopr.onboarding_presentation.ui_events_handler.FirstScreenOnBoardingUiEventsHandler
import com.aopr.onboarding_presentation.ui_events_handler.SecondScreenOnBoardingUiEventsHandler
import org.koin.androidx.compose.koinViewModel

@Composable
fun FirstOnBoardingScreen() {
    val viewModel = koinViewModel<FirstOnBoardingViewModel>()
    FirstScreenOnBoardingUiEventsHandler()
    Scaffold() { _ ->
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {
            NavigationButton(onDragComplete = { viewModel.onEvent(FirstScreenEvents.NavigateToSecondScreen) })

        }

    }
}

@Composable
fun SecondOnBoardingScreen() {
    val viewModel = koinViewModel<SecondOnBoardingViewModel>()
    SecondScreenOnBoardingUiEventsHandler()
    Scaffold() { _ ->
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {
            NavigationButton(onDragComplete = {
                viewModel.onEvent(SecondScreenEvents.NavigateToHomeScreen)
            })

        }

    }
}

@Composable
fun NavigationButton(onDragComplete: () -> Unit, modifier: Modifier = Modifier) {
    var dragPosition by remember {
        mutableStateOf(0f)
    }
    val maxDragDistance = 300f

    Card(
        modifier = modifier
            .fillMaxWidth(0.8f)
            .height(100.dp),
        colors = CardDefaults.cardColors(Color.Red),
        shape = RoundedCornerShape(20.dp)
    ) {
        Icon(
            imageVector = Icons.Rounded.KeyboardArrowRight,
            contentDescription = "",
            modifier = modifier
                .offset(x = dragPosition.dp)
                .draggable(
                    orientation = Orientation.Horizontal,
                    state = rememberDraggableState { delta ->
                        dragPosition = (dragPosition + delta).coerceIn(0f, maxDragDistance)

                    },
                    onDragStopped = {
                        if (dragPosition >= maxDragDistance) {
                            onDragComplete()

                        }
                        dragPosition = 0f
                    }
                )
        )
    }
}


