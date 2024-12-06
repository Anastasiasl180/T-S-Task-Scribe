package com.aopr.tasks_presentation.ui.ui_elements

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.aopr.tasks_presentation.events.creating_task_events.CreatingTaskEvents
import com.aopr.tasks_presentation.view_models.CreatingTaskViewModel
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import kotlin.math.*

enum class Hand {
    HOUR,
    MINUTE
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetForClock(
    onDismissRequest: () -> Unit,
    sheetState: SheetState,
    clockMode: CreatingTaskViewModel.ClockMode,
    timeOfTask: LocalTime?,
    timeOfSubTask: LocalTime?,
    updateTimeOfTask: () -> Unit,
    updateTimeOfSubTask: () -> Unit,
    hideClock: () -> Unit,
    heightOfScreen:Int
) {
    val heightScreen = heightOfScreen


    ModalBottomSheet(onDismissRequest = onDismissRequest, sheetState = sheetState) {

        var selectedTime by remember {
            mutableStateOf(timeOfTask ?: LocalTime.now())
        }
        var selectedTimeSub by remember {
            mutableStateOf(timeOfSubTask ?: LocalTime.now())
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height((heightScreen * 0.9f).dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                ClockPicker(
                    initialTime = when (clockMode) {
                        CreatingTaskViewModel.ClockMode.REMINDER_TASK -> {
                            selectedTime
                        }

                        CreatingTaskViewModel.ClockMode.SUB_REMINDER -> {
                            selectedTimeSub
                        }
                    }, onTimeChanged = { newTime ->
                        when (clockMode) {
                            CreatingTaskViewModel.ClockMode.REMINDER_TASK -> {
                                selectedTime = newTime
                            }

                            CreatingTaskViewModel.ClockMode.SUB_REMINDER -> {
                                selectedTimeSub = newTime

                            }
                        }

                    }
                )

                Text(
                    text =
                        when (clockMode) {
                            CreatingTaskViewModel.ClockMode.REMINDER_TASK -> {
                                selectedTime.format(DateTimeFormatter.ofPattern("HH:mm"))

                            }

                            CreatingTaskViewModel.ClockMode.SUB_REMINDER -> {
                                selectedTimeSub.format(DateTimeFormatter.ofPattern("HH:mm"))
                            }
                        },
                    modifier = Modifier.padding(top = 16.dp)
                )

                Button(onClick = {

                    when (clockMode) {
                        CreatingTaskViewModel.ClockMode.REMINDER_TASK -> {
                            updateTimeOfTask()
                            hideClock()
                        }

                        CreatingTaskViewModel.ClockMode.SUB_REMINDER -> {
                            updateTimeOfSubTask()
                            hideClock()
                        }
                    }
                }) {
                    Text("Save")
                }


            }

        }
    }
}

@Composable
fun ClockPicker(
    initialTime: LocalTime = LocalTime.now(),
    onTimeChanged: (LocalTime) -> Unit
) {
    var selectedTime by remember { mutableStateOf(initialTime) }

    val radius = 150.dp
    val radiusPx = with(LocalDensity.current) { radius.toPx() }

    val center = Offset(radiusPx, radiusPx)

    // Angles for hour and minute hands
    var hourAngle by remember {
        mutableFloatStateOf(
            (selectedTime.hour + selectedTime.minute / 60f) * 15f
        ) // 360 / 24 = 15 degrees per hour
    }
    var minuteAngle by remember {
        mutableFloatStateOf(
            selectedTime.minute * 6f
        ) // 360 / 60 = 6 degrees per minute
    }

    var draggingHand by remember { mutableStateOf<Hand?>(null) }

    Canvas(
        modifier = Modifier
            .size(radius * 2)
            .pointerInput(Unit) {
                detectDragGestures(
                    onDragStart = { offset ->
                        val touchPoint = offset
                        val x = touchPoint.x - center.x
                        val y = touchPoint.y - center.y

                        val touchAngle = ((Math.toDegrees(
                            atan2(
                                y.toDouble(),
                                x.toDouble()
                            )
                        ) + 360 + 90) % 360).toFloat()

                        // Compute the angle difference to the hour and minute hand
                        val hourAngleDiff = angleDifference(touchAngle, hourAngle)
                        val minuteAngleDiff = angleDifference(touchAngle, minuteAngle)

                        val angleThreshold = 15f // degrees

                        draggingHand = when {
                            hourAngleDiff < angleThreshold -> Hand.HOUR
                            minuteAngleDiff < angleThreshold -> Hand.MINUTE
                            else -> null
                        }
                    },
                    onDrag = { change, _ ->
                        val touchPoint = change.position
                        val x = touchPoint.x - center.x
                        val y = touchPoint.y - center.y

                        val angle = ((Math.toDegrees(
                            atan2(
                                y.toDouble(),
                                x.toDouble()
                            )
                        ) + 360 + 90) % 360).toFloat()

                        when (draggingHand) {
                            Hand.HOUR -> {
                                hourAngle = angle
                                val newHour = ((hourAngle / 15f).roundToInt()) % 24
                                val updatedTime = selectedTime.withHour(newHour)
                                selectedTime = updatedTime
                                onTimeChanged(updatedTime)
                            }

                            Hand.MINUTE -> {
                                minuteAngle = angle
                                val newMinute = ((minuteAngle / 6f).roundToInt()) % 60
                                val updatedTime = selectedTime.withMinute(newMinute)
                                selectedTime = updatedTime
                                onTimeChanged(updatedTime)
                            }

                            else -> {}
                        }
                    },
                    onDragEnd = {
                        draggingHand = null
                    }
                )
            }
    ) {

        // Draw clock face
        drawCircle(color = Color.LightGray, radius = size.minDimension / 2)

        // Draw hour hand with increased thickness
        val hourHandLength = size.minDimension / 4
        val hourHandAngleRad = Math.toRadians(hourAngle.toDouble() - 90)
        val hourHandEnd = Offset(
            x = center.x + hourHandLength * cos(hourHandAngleRad).toFloat(),
            y = center.y + hourHandLength * sin(hourHandAngleRad).toFloat()
        )
        drawLine(
            color = Color.Black,
            start = center,
            end = hourHandEnd,
            strokeWidth = 12f  // Adjust thickness as desired
        )

        // Draw minute hand with increased thickness
        val minuteHandLength = size.minDimension / 2.5f
        val minuteHandAngleRad = Math.toRadians(minuteAngle.toDouble() - 90)
        val minuteHandEnd = Offset(
            x = center.x + minuteHandLength * cos(minuteHandAngleRad).toFloat(),
            y = center.y + minuteHandLength * sin(minuteHandAngleRad).toFloat()
        )
        drawLine(
            color = Color.Black,
            start = center,
            end = minuteHandEnd,
            strokeWidth = 8f  // Adjust thickness as desired
        )

    }


}

fun angleDifference(angle1: Float, angle2: Float): Float {
    val diff = abs(angle1 - angle2) % 360f
    return if (diff > 180f) 360f - diff else diff
}

