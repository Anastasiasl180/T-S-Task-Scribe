package com.aopr.onboarding_presentation.ui

import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aopr.onboarding_presentation.R
import com.aopr.onboarding_presentation.events.first_screen_events.FirstScreenEvents
import com.aopr.onboarding_presentation.events.second_screen_events.SecondScreenEvents
import com.aopr.onboarding_presentation.model.FirstOnBoardingViewModel
import com.aopr.onboarding_presentation.model.SecondOnBoardingViewModel
import com.aopr.onboarding_presentation.ui_events_handler.FirstScreenOnBoardingUiEventsHandler
import com.aopr.onboarding_presentation.ui_events_handler.SecondScreenOnBoardingUiEventsHandler
import com.aopr.shared_ui.cardsView.background
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import kotlin.math.roundToInt


@Composable
fun FirstOnBoardingScreen() {
    FirstScreenOnBoardingUiEventsHandler()
    val background = background()
    val viewModel = koinViewModel<FirstOnBoardingViewModel>()
    Scaffold() { _ ->
        Box(modifier = Modifier.fillMaxSize().background(background), contentAlignment = Alignment.Center) {
            Box(
                modifier = Modifier
                    .fillMaxHeight(0.95f)
                    .fillMaxWidth(0.9f)
            ) {
                Column(modifier = Modifier.fillMaxSize()) {

                    Box(
                        modifier = Modifier
                            .fillMaxHeight(0.1f)
                            .fillMaxWidth(), contentAlignment = Alignment.CenterStart
                    ) {
                        Text(
                            text = "Task&Scribe",
                            color = Color.White,
                            fontSize = 25.sp,
                            fontFamily = FontFamily(Font(com.aopr.shared_ui.R.font.roboto))
                        )
                    }
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(0.8f), contentAlignment = Alignment.BottomStart
                    ) {
                        Column {


                            Text(
                                text = "Your\nperformance.",
                                lineHeight = 50.sp,
                                fontSize = 50.sp,
                                fontFamily = FontFamily(Font(com.aopr.shared_ui.R.font.roboto))
                            )
                            Text(
                                text = "Your organization.",
                                lineHeight = 50.sp,
                                fontSize = 50.sp,
                                fontFamily = FontFamily(Font(com.aopr.shared_ui.R.font.roboto))
                            )
                        }

                    }
                }
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomEnd) {
                    Column(
                        modifier = Modifier
                            .height(120.dp)
                            .fillMaxWidth(), verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        Box(
                            modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.BottomStart
                        ) {
                            HorizontalPageIndicator(currentPage = 0, totalPages = 2)
                        }

                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.CenterEnd
                        ) {
                        NavigationButton(onDragComplete = {
                            viewModel.onEvent(FirstScreenEvents.NavigateToSecondScreen)
                        })}
                    }
                }

            }


        }

    }
}

@Composable
fun SecondOnBoardingScreen() {
    val viewModel = koinViewModel<SecondOnBoardingViewModel>()
    val background = background()
    SecondScreenOnBoardingUiEventsHandler()
    Scaffold() { _ ->

        Box(modifier = Modifier.fillMaxSize().background(background), contentAlignment = Alignment.Center) {

            Box(
                modifier = Modifier
                    .fillMaxHeight(0.95f)
                    .fillMaxWidth(0.9f)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.7f), contentAlignment = Alignment.BottomStart
                ) {
                    Column {
                        Text(
                            text = "Your",
                            lineHeight = 50.sp,
                            fontSize = 45.sp,
                            fontFamily = FontFamily(Font(com.aopr.shared_ui.R.font.roboto))
                        )
                        Text(
                            text = "Your all-in-one",
                            lineHeight = 50.sp,
                            fontSize = 45.sp,
                            fontFamily = FontFamily(Font(com.aopr.shared_ui.R.font.roboto))
                        )
                       /* Text(text = stringResource())*/
                        Text(
                            text = "hub for organizing tasks, notes, reminders, and ideas effortlessly.",
                            lineHeight = 50.sp,
                            fontSize = 45.sp,
                            fontFamily = FontFamily(Font(com.aopr.shared_ui.R.font.roboto))
                        )
                    }

                }

                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomEnd) {
                    Column(
                        modifier = Modifier
                            .height(120.dp)
                            .fillMaxWidth(), verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        Box(
                            modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.BottomStart
                        ) {
                            HorizontalPageIndicator(currentPage = 1, totalPages = 2)
                        }

                        Row(
                            modifier = Modifier
                                .height(95.dp)
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.End,
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            Button2(onClickUp = {
                                viewModel.onEvent(SecondScreenEvents.NavigateToBack)
                            })
                            Spacer(modifier = Modifier.padding(5.dp))
                            NavigationButton(onDragComplete = {
                                viewModel.onEvent(SecondScreenEvents.NavigateToHomeScreen)
                            })
                        }
                    }

                }
            }
        }

    }
}

@Composable
fun NavigationButton(onDragComplete: () -> Unit, modifier: Modifier = Modifier) {
    var offsetX by remember { mutableStateOf(0f) }
    val animatedOffsetX = remember { Animatable(0f) }
    val scope = rememberCoroutineScope()

    var cardWidth by remember { mutableStateOf(0f) }

    Box(
        modifier = modifier
            .onGloballyPositioned { layoutCoordinates ->
                cardWidth = layoutCoordinates.size.width.toFloat()
            }
            .width(290.dp)
            .height(90.dp)
            .background(Color.DarkGray, shape = RoundedCornerShape(50.dp)),
        contentAlignment = Alignment.Center
    ) {
        val dragThreshold = cardWidth * 0.6f
        Row(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(0.94f),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Box(
                modifier = Modifier
                    .offset { IntOffset(animatedOffsetX.value.roundToInt(), 0) }
                    .height(75.dp)
                    .width(75.dp)
                    .background(Color.White, shape = CircleShape)
                    .pointerInput(Unit) {
                        detectDragGestures(
                            onDrag = { change, dragAmount ->
                                change.consume()
                                offsetX =
                                    (offsetX + dragAmount.x).coerceIn(0f, cardWidth - 90.dp.toPx())
                                scope.launch {
                                    animatedOffsetX.snapTo(offsetX)
                                }
                            },
                            onDragEnd = {
                                scope.launch {
                                    if (offsetX >= dragThreshold) {
                                        onDragComplete()
                                    }
                                    animatedOffsetX.animateTo(0f)
                                    offsetX = 0f
                                }
                            }
                        )
                    },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowForward,
                    contentDescription = "",
                    modifier = modifier
                        .size(25.dp),
                    tint = Color.DarkGray,
                )
            }
        }
    }
}

@Composable
fun Button2(onClickUp: () -> Unit) {
    Box(
        modifier = Modifier
            .height(75.dp)
            .width(75.dp)
            .clickable { onClickUp() }
            .background(Color.Transparent, shape = CircleShape)
            .border(width = 2.dp, color = Color.DarkGray, shape = CircleShape),
        contentAlignment = Alignment.Center
    ) {

        Icon(
            imageVector = Icons.Default.ArrowBack,
            contentDescription = "",
            modifier = Modifier
                .size(25.dp)
        )
    }
}

@Composable
fun HorizontalPageIndicator(
    currentPage: Int,
    totalPages: Int,
    activeColor: Color = Color.White,
    inactiveColor: Color = Color.Gray
) {
    Row(
        horizontalArrangement = Arrangement.Start,
        modifier = Modifier.fillMaxWidth()
    ) {
        repeat(totalPages) { index ->
            val isActive = index == currentPage
            val color = if (isActive) activeColor else inactiveColor

            val indicatorWidth = if (isActive) 20.dp else 8.dp
            val indicatorHeight = 4.dp

            Box(
                modifier = Modifier
                    .padding(horizontal = 4.dp)
                    .size(width = indicatorWidth, height = indicatorHeight)
                    .background(color, shape = RoundedCornerShape(50))
            )
        }
    }
}

