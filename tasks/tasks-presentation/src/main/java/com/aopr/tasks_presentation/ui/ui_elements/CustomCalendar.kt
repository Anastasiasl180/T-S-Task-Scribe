package com.aopr.tasks_presentation.ui.ui_elements

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.aopr.tasks_domain.models.Task
import java.time.LocalDate
import java.time.format.DateTimeFormatter


@Composable
fun CustomCalendar(
    initialSelectedDate: LocalDate? = null,
    onDateSelected: (LocalDate) -> Unit,
    onDismiss: () -> Unit,
    listOfDates: List<LocalDate?>,
    getTasks: (LocalDate) -> Unit, listOfTasks: List<Task?>
) {
    val currentDate = remember { mutableStateOf(LocalDate.now()) }
    val selectedDate = remember { mutableStateOf(initialSelectedDate) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // CalendarHeader with selectedDate
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

        // Weekday labels
        WeekdayLabels()

        // Dates grid
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

        // Save and Cancel buttons
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.End
        ) {
            TextButton(onClick = onDismiss) {
                Text(text = "Cancel")
            }
            Spacer(modifier = Modifier.width(8.dp))
            Button(
                onClick = {
                    selectedDate.value?.let {
                        onDateSelected(it)
                    }
                    onDismiss()
                },
                enabled = selectedDate.value != null
            ) {
                Text(text = "Save")
            }
        }
    }
}

@Composable
fun CalendarHeader(
    currentDate: LocalDate,
    selectedDate: LocalDate?,
    onPreviousMonth: () -> Unit,
    onNextMonth: () -> Unit
) {
    Column {
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

    Column {
        dates.chunked(7).forEach { week ->
            Row(modifier = Modifier.fillMaxWidth()) {
                week.forEach { date ->
                    val isSelected = date == selectedDate
                    val datesWithTasksColor =
                        if (listOfDates.contains(date)) Color.Red else Color.Black
                    val backgroundColor =
                        if (isSelected) MaterialTheme.colorScheme.primary else Color.Transparent

                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .aspectRatio(1f)
                            .padding(4.dp)
                            .background(backgroundColor, shape = CircleShape)
                            .clickable(enabled = date != null) {
                                date?.let { onDateSelected(it) }
                                if (date != null) {
                                    getTasks(date)
                                }
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        date?.let {
                            Text(
                                text = it.dayOfMonth.toString(),
                                color = datesWithTasksColor,
                            )
                        }
                    }
                }
            }
        }
        if (listOfTasks.isNotEmpty()) {
            Column {
                listOfTasks.forEach { task ->
                    Card(modifier = Modifier.fillMaxWidth()) {
                        if (task != null) {
                            Text(text = task.tittle)
                        }
                    }
                }

            }
        }else{
            Text(text = "NoTask")
        }
    }
}

