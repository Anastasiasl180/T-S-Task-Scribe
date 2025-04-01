package com.example.calendar_presentation.ui

import android.util.Log
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.aopr.shared_domain.colors_for_theme.Themes
import com.aopr.shared_ui.util.global_view_model.GlobalViewModel
import com.aopr.shared_ui.util.global_view_model.GlobalViewModelStoreOwner
import com.aopr.tasks_domain.models.Task
import com.example.calendar_presentation.view_model.CalendarViewModel
import com.example.calendar_presentation.view_model.events.CalendarEvents
import com.example.calendar_presentation.view_model.ui_event_handler.CalendarUiEventHandler
import org.koin.androidx.compose.koinViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarScreen() {
    val calendarViewModel = koinViewModel<CalendarViewModel>()
    val listOfDates = calendarViewModel.datesWithTasks
    CalendarUiEventHandler()
    Scaffold(

        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    calendarViewModel.onEvent(CalendarEvents.NavigateToCreateNewTaskOrExhistingTask(null))
                }, modifier = Modifier
                    .clip(CircleShape)
                    .size(60.dp), containerColor = Color.DarkGray.copy(alpha = 0.6f)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "AddFloatingActionButton",
                    modifier = Modifier.size(20.dp)
                )


            }
        }, topBar = {
            TopAppBar(colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent),
                navigationIcon = {
                    IconButton(
                        onClick = { calendarViewModel.onEvent(CalendarEvents.NavigateBack) },
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(Color.DarkGray.copy(alpha = 0.6f))
                            .size(50.dp)
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Default.KeyboardArrowLeft,
                            contentDescription = "",
                            tint = Color.White, modifier = Modifier.size(20.dp)
                        )
                    }
                },
                title = { /*TODO*/ })
        }

    ) { _ ->
        Calendar(
            getTasksByDate = { calendarViewModel.onEvent(CalendarEvents.GetTasksByDate(it)) },
            listOfDatesWithTask = listOfDates,
            listOfTasks = calendarViewModel.tasksByDate,
            navigateToTask = {
                calendarViewModel.onEvent(CalendarEvents.NavigateToCreateNewTaskOrExhistingTask(it))
            }
        )
    }


}


@Composable
fun Calendar(
    getTasksByDate: (LocalDate) -> Unit,
    listOfDatesWithTask: List<LocalDate?>,
    listOfTasks: List<Task?>,
    navigateToTask: (Int) -> Unit,

    ) {


    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        CustomCalendar(
            onDateSelected = { selectedDate ->

            },
            listOfDates = listOfDatesWithTask, getTasks = {
                getTasksByDate(it)
            }, listOfTasks = listOfTasks, navigateToTask = {
                navigateToTask(it)
            }
        )
    }


}


@Composable
fun CustomCalendar(
    initialSelectedDate: LocalDate? = null,
    onDateSelected: (LocalDate) -> Unit,
    listOfDates: List<LocalDate?>,
    getTasks: (LocalDate) -> Unit,
    listOfTasks: List<Task?>, navigateToTask: (Int) -> Unit
) {
    val currentDate = remember { mutableStateOf(LocalDate.now()) }
    val selectedDate = remember { mutableStateOf(initialSelectedDate) }

    Column(
        modifier = Modifier
            .fillMaxSize()

    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.1f), contentAlignment = Alignment.CenterEnd
        ) {
            Row(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(0.5f)
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Spacer(modifier = Modifier.width(8.dp))
                Button(
                    onClick = {
                        selectedDate.value?.let {
                            onDateSelected(it)
                        }
                    }, colors = ButtonDefaults.buttonColors(Color.White.copy(alpha = 0.2f)),
                    border = BorderStroke(
                        width = 0.5.dp,
                        color = Color.White.copy(alpha = 0.5f)
                    ),
                    enabled = selectedDate.value != null
                ) {
                    Text(
                        text = stringResource(id = com.aopr.shared_ui.R.string.save),
                        color = Color.White
                    )
                }
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
            }, listOfTasks = listOfTasks, navigateToTask = {
                navigateToTask(it)
            }
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
    listOfTasks: List<Task?>,
    navigateToTask: (Int) -> Unit
) {
    val globalViewModel =
        koinViewModel<GlobalViewModel>(viewModelStoreOwner = GlobalViewModelStoreOwner)
    val chosenTheme = globalViewModel.chosenTheme.collectAsState()
    val colorForDate = when (chosenTheme.value) {
        Themes.BLUE -> {
            MaterialTheme.colorScheme.onPrimary
        }

        Themes.VIOLET -> {
            MaterialTheme.colorScheme.secondary
        }

        Themes.HAKI -> {
            MaterialTheme.colorScheme.primaryContainer
        }

        Themes.METALLIC -> {
            MaterialTheme.colorScheme.secondary
        }

        Themes.`DUSKY_EVENING` -> {
            MaterialTheme.colorScheme.onBackground
        }

        Themes.ORANGE -> {
            MaterialTheme.colorScheme.onBackground
        }

        Themes.PASTEL -> {
            MaterialTheme.colorScheme.secondary
        }
    }
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
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    week.forEach { date ->
                        val isSelected = date == selectedDate
                        val isPastDate = date?.isBefore(LocalDate.now()) == true
                        val isTaskDate = listOfDates.contains(date)

                        val textColor = when {
                            isPastDate -> if (isTaskDate) colorForDate.copy(alpha = 0.5f) else Color.Gray
                            isTaskDate -> colorForDate
                            else -> Color.White
                        }

                        val backgroundColor = if (isSelected) {
                            Color.Gray
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
                                .clickable(enabled = date != null /*&& !isPastDate*/) {
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
                Spacer(modifier = Modifier.height(30.dp))
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxHeight()
                            .fillMaxWidth(0.95f)
                            .clip(RoundedCornerShape(30.dp)),
                        verticalArrangement = Arrangement.spacedBy(20.dp)
                    ) {
                        item {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth(0.2f)
                                    .fillMaxHeight(0.1f),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    text = stringResource(id = com.aopr.notes_presentation.R.string.tasks),
                                    color = Color.White
                                )
                            }
                        }

                        listOfTasks.forEach { task ->
                            item {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(100.dp)
                                        .padding(vertical = 5.dp),
                                    horizontalArrangement = Arrangement.Center,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Card(
                                        modifier = Modifier
                                            .fillMaxHeight()
                                            .fillMaxWidth(0.9f)
                                            .clickable {
                                                if (task != null) {
                                                    navigateToTask(task.id)
                                                }
                                            }
                                    ) {
                                        if (task != null) {
                                            Row(
                                                modifier = Modifier
                                                    .fillMaxSize()
                                                    .padding(start = 10.dp),
                                                verticalAlignment = Alignment.CenterVertically
                                            ) {
                                                Text(text = task.tittle, color = Color.White)
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            } else {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(start = 10.dp, top = 15.dp),
                ) {
                    Text(text = stringResource(id = com.aopr.notes_presentation.R.string.noTasks))
                }
            }
        }
    }
}
