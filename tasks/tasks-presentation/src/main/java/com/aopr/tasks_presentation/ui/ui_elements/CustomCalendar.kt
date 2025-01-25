package com.aopr.tasks_presentation.ui.ui_elements

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.aopr.notes_presentation.R
import com.aopr.tasks_domain.models.Task
import com.aopr.tasks_presentation.events.creating_task_events.CreatingTaskEvents
import com.aopr.tasks_presentation.view_models.CreatingTaskViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetForCalendar(
    onDismiss: () -> Unit,
    sheetState: SheetState,
    initialSelectedDate: LocalDate?,
    calendarMode: CreatingTaskViewModel.CalendarMode,
    updateDateOfTaskToBeDone: (LocalDate) -> Unit,
    updateDateOfTaskForReminder: (LocalDate) -> Unit,
    updateDateForSubtask: (LocalDate) -> Unit,
    hideCalendar: () -> Unit,
    getTasksByDate: (LocalDate) -> Unit,
    heightScreen: Int,
    listOfDatesWithTask: List<LocalDate?>,
    listOfTasks: List<Task?>

) {

    ModalBottomSheet(onDismissRequest = onDismiss, sheetState = sheetState) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height((heightScreen * 0.9f).dp)
        ) {
            CustomCalendar(
                initialSelectedDate = initialSelectedDate,
                onDateSelected = { selectedDate ->
                    when (calendarMode) {
                        CreatingTaskViewModel.CalendarMode.TASK_DONE ->
                            updateDateOfTaskToBeDone(selectedDate)

                        CreatingTaskViewModel.CalendarMode.REMINDER ->
                            updateDateOfTaskForReminder(selectedDate)

                        CreatingTaskViewModel.CalendarMode.SUB_REMINDER ->
                            updateDateForSubtask(selectedDate)
                    }
                },
                onDismiss = {
                    hideCalendar()
                }, listOfDates = listOfDatesWithTask, getTasks = {
                    getTasksByDate(it)
                }, listOfTasks = listOfTasks
            )
        }
    }


}


@Composable
fun CustomCalendar(
    initialSelectedDate: LocalDate? = null,
    onDateSelected: (LocalDate) -> Unit,
    onDismiss: () -> Unit,
    listOfDates: List<LocalDate?>,
    getTasks: (LocalDate) -> Unit,
    listOfTasks: List<Task?>
) {
    val currentDate = remember { mutableStateOf(LocalDate.now()) }
    val selectedDate = remember { mutableStateOf(initialSelectedDate) }

    Column(
        modifier = Modifier
            .fillMaxSize()

    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.End
        ) {
            TextButton(onClick = onDismiss,  colors = ButtonDefaults.buttonColors(Color.White.copy(alpha = 0.2f)),
                border = BorderStroke(
                    width = 0.5.dp,
                    color = Color.White.copy(alpha = 0.5f)
                )
            ) {
                Text(text = stringResource(id = com.aopr.shared_ui.R.string.cancel), color = Color.White)
            }
            Spacer(modifier = Modifier.width(8.dp))
            Button(
                onClick = {
                    selectedDate.value?.let {
                        onDateSelected(it)
                    }
                    onDismiss()
                },  colors = ButtonDefaults.buttonColors(Color.White.copy(alpha = 0.2f)),
                border = BorderStroke(
                    width = 0.5.dp,
                    color = Color.White.copy(alpha = 0.5f)
                ),
                enabled = selectedDate.value != null
            ) {
                Text(text = stringResource(id = com.aopr.shared_ui.R.string.save), color = Color.White)
            }
        }
        CalendarHeader(
            currentDate = currentDate.value,
            selectedDate = selectedDate.value,
            onPreviousMonth = {
                currentDate.value = currentDate.value.minusMonths(1)
            },
            onNextMonth = {
                currentDate.value = currentDate.value.plusMonths(1)
            }
        )

        WeekdayLabels()

        DatesGrid(
            currentDate = currentDate.value,
            selectedDate = selectedDate.value,
            listOfDates = listOfDates,
            onDateSelected = { date ->
                selectedDate.value = date
            },
            getTasks = {
                getTasks(it)
            }, listOfTasks = listOfTasks
        )

        Spacer(modifier = Modifier.weight(1f))

    }
}

@Composable
fun CalendarHeader(
    currentDate: LocalDate,
    selectedDate: LocalDate?,
    onPreviousMonth: () -> Unit,
    onNextMonth: () -> Unit
) {
    Column() {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onPreviousMonth) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Previous Month")
            }
            Text(
                text = currentDate.format(DateTimeFormatter.ofPattern("MMMM yyyy")),
                style = MaterialTheme.typography.titleLarge
            )
            IconButton(onClick = onNextMonth) {
                Icon(imageVector = Icons.Default.ArrowForward, contentDescription = "Next Month")
            }
        }

        selectedDate?.let {
            Text(
                text = "Selected: ${it.format(DateTimeFormatter.ofPattern("dd MMM yyyy"))}",
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(bottom = 8.dp),

                )
        }
    }
}

@Composable
fun WeekdayLabels() {
    val daysOfWeek = listOf("Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat")
    Row(modifier = Modifier.fillMaxWidth()) {
        daysOfWeek.forEach { day ->
            Text(
                text = day,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )
        }
    }
}

@Composable
fun DatesGrid(
    currentDate: LocalDate,
    selectedDate: LocalDate?,
    onDateSelected: (LocalDate) -> Unit,
    listOfDates: List<LocalDate?>,
    getTasks: (LocalDate) -> Unit,
    listOfTasks: List<Task?>
) {
    val firstDayOfMonth = currentDate.withDayOfMonth(1)
    val firstDayOfWeek = (firstDayOfMonth.dayOfWeek.value % 7)
    val daysInMonth = currentDate.lengthOfMonth()

    val dates = mutableListOf<LocalDate?>()
    for (i in 0 until firstDayOfWeek) {
        dates.add(null)
    }
    for (day in 1..daysInMonth) {
        dates.add(firstDayOfMonth.withDayOfMonth(day))
    }

    while (dates.size % 7 != 0) {
        dates.add(null)
    }
    Box(modifier = Modifier.fillMaxSize()) {
    Column(modifier = Modifier.fillMaxSize()) {
        dates.chunked(7).forEach { week ->
            Row(modifier = Modifier
                .fillMaxWidth()) {
                week.forEach { date ->
                    val isSelected = date == selectedDate
                    val isPastDate = date?.isBefore(LocalDate.now()) == true
                    val isTaskDate = listOfDates.contains(date)

                    val textColor = when {
                        isPastDate -> Color.Black
                        isTaskDate -> Color.Blue
                        else -> Color.Gray
                    }

                    val backgroundColor = if (isSelected) {
                        Color.White
                    } else {
                        Color.Transparent
                    }

                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .aspectRatio(1f)
                            .padding(4.dp)
                            .background(
                                color = backgroundColor.takeIf { date != null }
                                    ?: Color.Transparent,
                                shape = CircleShape
                            )
                            .clickable(enabled = date != null && !isPastDate) {
                                date?.let {
                                    onDateSelected(it)
                                    getTasks(it)
                                }
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        date?.let {
                            Text(
                                text = it.dayOfMonth.toString(),
                                color = textColor,
                            )
                        }
                    }
                }
            }
        }

        if (listOfTasks.isNotEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxSize(), verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
               Row(modifier = Modifier.fillMaxWidth(0.2f).fillMaxHeight(0.15f), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {


                   Text(text = stringResource(id = R.string.tasks), color = Color.White)
               }
                listOfTasks.forEach { task ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(0.8f)
                            .fillMaxHeight(0.3f),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Card(
                            modifier = Modifier
                                .fillMaxHeight().fillMaxWidth(0.9f)
                        ) {
                            if (task != null) {
                                Row(
                                    modifier = Modifier.fillMaxSize().padding(start = 10.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(text = task.tittle, color = Color.White)
                                }
                            }
                        }
                    }
                }
            }
        } else {
            Text(text = stringResource(id = R.string.noTasks))
        }
    }
}
}
