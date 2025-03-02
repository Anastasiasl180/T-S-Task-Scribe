package com.aopr.onboarding_presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aopr.onboarding_presentation.R
import com.aopr.onboarding_presentation.view_models.events.first_screen_events.FirstScreenEvents
import com.aopr.onboarding_presentation.view_models.events.second_screen_events.SecondScreenEvents
import com.aopr.onboarding_presentation.view_models.FirstOnBoardingViewModel
import com.aopr.onboarding_presentation.view_models.SecondOnBoardingViewModel
import com.aopr.onboarding_presentation.ui.ui_elements.BackButton
import com.aopr.onboarding_presentation.ui.ui_elements.HorizontalPageIndicator
import com.aopr.onboarding_presentation.ui.ui_elements.NavigationButton
import com.aopr.onboarding_presentation.view_models.ui_events_handler.FirstScreenOnBoardingUiEventsHandler
import com.aopr.onboarding_presentation.view_models.ui_events_handler.SecondScreenOnBoardingUiEventsHandler
import org.koin.androidx.compose.koinViewModel


@Composable
fun FirstOnBoardingScreen() {
    FirstScreenOnBoardingUiEventsHandler()
    val backgroundTheme = Brush.linearGradient(
        colorStops = arrayOf(
            0.1f to  Color(0xFF1D0E36),
            0.4f to  Color(0xFF461851),
            0.6f to Color(0xFF1E0C27),
            1.0f to  Color(0xFF000000),

            ), start = Offset.Zero,
        end = Offset.Infinite)
    val viewModel = koinViewModel<FirstOnBoardingViewModel>()
    Scaffold() { _ ->
        Box(modifier = Modifier.fillMaxSize().background(backgroundTheme), contentAlignment = Alignment.Center) {
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
                            text = stringResource(R.string.Task_Scribe),
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
                                text = stringResource(R.string.your),
                                lineHeight = 50.sp,
                                fontSize = 50.sp,
                                fontFamily = FontFamily(Font(com.aopr.shared_ui.R.font.roboto))
                            )
                            Text(
                                text = stringResource(R.string.performance),
                                lineHeight = 50.sp,
                                fontSize = 50.sp,
                                fontFamily = FontFamily(Font(com.aopr.shared_ui.R.font.roboto))
                            )
                            Text(
                                text = stringResource(R.string.your_organization),
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
    val backgroundTheme = Brush.linearGradient(
        colorStops = arrayOf(
            0.1f to  Color(0xFF1D0E36),
            0.4f to  Color(0xFF461851),
            0.6f to Color(0xFF1E0C27),
            1.0f to  Color(0xFF000000),

            ), start = Offset.Zero,
        end = Offset.Infinite)
    SecondScreenOnBoardingUiEventsHandler()
    Scaffold() { _ ->

        Box(modifier = Modifier.fillMaxSize().background(backgroundTheme), contentAlignment = Alignment.Center) {

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
                            text = stringResource(R.string.your),
                            lineHeight = 50.sp,
                            fontSize = 45.sp,
                            fontFamily = FontFamily(Font(com.aopr.shared_ui.R.font.roboto))
                        )
                        Text(
                            text = stringResource(R.string.all_in_one),
                            lineHeight = 50.sp,
                            fontSize = 45.sp,
                            fontFamily = FontFamily(Font(com.aopr.shared_ui.R.font.roboto))
                        )

                        Text(
                            text = stringResource((R.string.description)),
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

                            BackButton(onClickUp = {
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
