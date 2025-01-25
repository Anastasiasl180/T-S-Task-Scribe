package com.aopr.tasks_presentation.ui.ui_elements

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.aopr.notes_presentation.R
import com.aopr.tasks_presentation.view_models.CreatingTaskViewModel
import kotlinx.coroutines.delay
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import kotlin.math.abs
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.roundToInt
import kotlin.math.sin

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
    updateTimeOfTask: (LocalTime?) -> Unit,
    updateTimeOfSubTask: (LocalTime?) -> Unit,
    hideClock: () -> Unit,
    heightOfScreen: Int
) {
    val heightScreen = heightOfScreen

    ModalBottomSheet(onDismissRequest = onDismissRequest, sheetState = sheetState) {

        // Initialize selectedTime based on clockMode
        var selectedTime by remember {
            mutableStateOf(
                when (clockMode) {
                    CreatingTaskViewModel.ClockMode.REMINDER_TASK -> timeOfTask
                    CreatingTaskViewModel.ClockMode.SUB_REMINDER -> timeOfSubTask
                }
            )
        }

        // State for current local time
        var currentTime by remember { mutableStateOf(LocalTime.now()) }

        // Update currentTime every second
        LaunchedEffect(Unit) {
            while (true) {
                currentTime = LocalTime.now()
                delay(1000L)
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height((heightScreen * 0.9f).dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                // Display Current Local Time
                Text(
                    text = "Current Time: ${currentTime.format(DateTimeFormatter.ofPattern("HH:mm:ss"))}",

                    modifier = Modifier.padding(bottom = 8.dp)
                )

                // Display Selected Time or Placeholder
                Text(
                    text = if (selectedTime != null) {
                        "Selected Time: ${selectedTime!!.format(DateTimeFormatter.ofPattern("HH:mm"))}"
                    } else {
                        stringResource(id = R.string.selectedTimeNotSet)
                    },
                    modifier = Modifier.padding(bottom = 16.dp), color = Color.White
                )

                // ClockPicker should handle null initialTime gracefully
                ClockPicker(
                    initialTime = selectedTime ?: LocalTime.now(),
                    onTimeChanged = { newTime ->
                        selectedTime = newTime
                    }
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.3f),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {

                    Button(
                        onClick = {
                            selectedTime?.let { time ->
                                when (clockMode) {
                                    CreatingTaskViewModel.ClockMode.REMINDER_TASK -> {
                                        updateTimeOfTask(time)
                                    }

                                    CreatingTaskViewModel.ClockMode.SUB_REMINDER -> {
                                        updateTimeOfSubTask(time)
                                    }
                                }
                            }
                            hideClock()
                        },
                        colors = ButtonDefaults.buttonColors(Color.White.copy(alpha = 0.2f)),
                        border = BorderStroke(
                            width = 0.5.dp,
                            color = Color.White.copy(alpha = 0.5f)
                        )

                    ) {
                        Text(
                            text = stringResource(id = com.aopr.shared_ui.R.string.save),
                            color = Color.White
                        )
                    }

                    Button(
                        onClick = {
                            when (clockMode) {
                                CreatingTaskViewModel.ClockMode.REMINDER_TASK -> {
                                    updateTimeOfTask(null)
                                }

                                CreatingTaskViewModel.ClockMode.SUB_REMINDER -> {
                                    updateTimeOfSubTask(null)
                                }
                            }
                            hideClock()
                        },
                        colors = ButtonDefaults.buttonColors(Color.White.copy(alpha = 0.2f)),
                        border = BorderStroke(
                            width = 0.5.dp,
                            color = Color.White.copy(alpha = 0.5f)
                        )

                    ) {
                        Text(
                            text = stringResource(id = com.aopr.shared_ui.R.string.cancel),
                            color = Color.White
                        )
                    }
                }
            }
        }
    }

}


@Composable
fun ClockPicker(
    initialTime: LocalTime?,
    onTimeChanged: (LocalTime?) -> Unit
) {
    // Keep the same placeholder for null
    if (initialTime == null) {
        Box(
            modifier = Modifier.size(300.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "No time selected")
        }
        return
    }

    var selectedTime by remember { mutableStateOf(initialTime) }

    val radius = 150.dp
    val radiusPx = with(LocalDensity.current) { radius.toPx() }
    val center = Offset(radiusPx, radiusPx)

    // --- 24-HOUR: Each hour is 15 degrees (360/24) ---
    // Instead of (selectedTime.hour % 12 + minutes/60f) * 30f, use:
    var hourAngle by remember {
        mutableFloatStateOf(
            (selectedTime.hour + selectedTime.minute / 60f) * 15f
        )
    }

    // Minutes stay the same (each minute is 6°, 0..59 => 0..354)
    var minuteAngle by remember {
        mutableFloatStateOf(selectedTime.minute * 6f)
    }

    var draggingHand by remember { mutableStateOf<Hand?>(null) }

    Canvas(
        modifier = Modifier
            .size(radius * 2)
            .pointerInput(Unit) {
                detectDragGestures(
                    onDragStart = { offset ->
                        val x = offset.x - center.x
                        val y = offset.y - center.y
                        val touchAngle = ((Math.toDegrees(atan2(y.toDouble(), x.toDouble())) +
                                360 + 90) % 360).toFloat()

                        val hourAngleDiff = angleDifference(touchAngle, hourAngle)
                        val minuteAngleDiff = angleDifference(touchAngle, minuteAngle)
                        val angleThreshold = 15f

                        draggingHand = when {
                            hourAngleDiff < angleThreshold -> Hand.HOUR
                            minuteAngleDiff < angleThreshold -> Hand.MINUTE
                            else -> null
                        }
                    },
                    onDrag = { change, _ ->
                        val x = change.position.x - center.x
                        val y = change.position.y - center.y
                        val angle = ((Math.toDegrees(atan2(y.toDouble(), x.toDouble())) +
                                360 + 90) % 360).toFloat()

                        when (draggingHand) {
                            Hand.HOUR -> {
                                // --- 24-hour hour calculation ---
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

        // Hour hand
        val hourHandLength = size.minDimension / 4
        val hourHandAngleRad = Math.toRadians(hourAngle - 90.0)
        val hourHandEnd = Offset(
            x = center.x + hourHandLength * cos(hourHandAngleRad).toFloat(),
            y = center.y + hourHandLength * sin(hourHandAngleRad).toFloat()
        )
        drawLine(
            color = Color.Black,
            start = center,
            end = hourHandEnd,
            strokeWidth = 12f
        )

        // Minute hand
        val minuteHandLength = size.minDimension / 2.5f
        val minuteHandAngleRad = Math.toRadians(minuteAngle - 90.0)
        val minuteHandEnd = Offset(
            x = center.x + minuteHandLength * cos(minuteHandAngleRad).toFloat(),
            y = center.y + minuteHandLength * sin(minuteHandAngleRad).toFloat()
        )
        drawLine(
            color = Color.Black,
            start = center,
            end = minuteHandEnd,
            strokeWidth = 8f
        )
    }
}

// Helper to find angle differences (0..180)
fun angleDifference(angle1: Float, angle2: Float): Float {
    val diff = abs(angle1 - angle2) % 360f
    return if (diff > 180f) 360f - diff else diff
}

