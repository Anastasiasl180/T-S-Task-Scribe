package com.aopr.tasks_presentation.ui.ui_elements

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.aopr.tasks_domain.models.Subtasks
import com.aopr.tasks_presentation.events.creating_task_events.CreatingTaskEvents
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@Composable
fun SubTaskCard(
    index: Int,
    tittle: String,
    data: LocalDate?,
    time: LocalTime?,
    showCalendarForReminder: (Int) -> Unit,
    showClockForReminder: (Int) -> Unit,
    onValueDescriptionChange: (Int, String) -> Unit,
    onIsCompletedChange: (Int, Boolean) -> Unit,
    onDelete: (Int) -> Unit,
    isCompleted: Boolean,
    clearDataOfSubTask:()-> Unit,
    clearTimeOfSubTask:()-> Unit
) {
    Card(
        modifier = Modifier
            .height(
                500.dp
            )
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),

            ) {
            TextField(
                placeholder = {
                    Text(text = "Enter Subtask")
                },
                value = tittle,
                onValueChange = { newValue -> onValueDescriptionChange(index, newValue) },
                modifier = Modifier.weight(1f),
                singleLine = true,
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                )
            )

            Card(modifier = Modifier) {
                Row() {
                    Text(
                        text = data?.format(
                            DateTimeFormatter.ofPattern(
                                "dd MMM yyyy"
                            )
                        )
                            ?: "Select Date for reminder",
                    )
                    Button(onClick = { showCalendarForReminder(index) }) {

                    }
                    IconButton(onClick = {
                        clearDataOfSubTask()
                    }) {
                        Icon(imageVector = Icons.Default.Delete,"")
                    }


                }
                Row() {
                    Text(
                        text = time?.format(DateTimeFormatter.ofPattern("HH:mm"))
                            ?: "",
                    )
                    Button(onClick = { showClockForReminder(index) }) {

                    }
                    IconButton(onClick = {
                        clearTimeOfSubTask()
                    }) {
                        Icon(imageVector = Icons.Default.Delete,"")
                    }


                }

            }



            Checkbox(checked = isCompleted, onCheckedChange = { isChecked ->
                onIsCompletedChange(index, isChecked)
            })
            IconButton(onClick = { onDelete(index) }) {
                Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete Subtask")
            }
        }
    }
}

@Composable
fun SubTasksList(
    subtasks: List<Subtasks>,
    onUpdateIsCompleted: (Int, Boolean) -> Unit,
    onUpdateDescription: (Int, String) -> Unit,
    onDeleteSubTask: (Int) -> Unit,
    data: LocalDate?,
    time: LocalTime?,
    showCalendarForReminder: (Int) -> Unit,
    showClockForReminder: (Int) -> Unit,
    clearDataOfSubTask:()-> Unit,
    clearTimeOfSubTask:()-> Unit
) {
    Column {
        subtasks.forEachIndexed { index, subtask ->
            SubTaskCard(
                index = index,
                tittle = subtask.description,
                isCompleted = subtask.isCompleted,
                data = subtask.date,
                time = subtask.time,
                onValueDescriptionChange = { i, newDescription ->
                    onUpdateDescription(i, newDescription)

                },
                onIsCompletedChange = { i, isChecked ->
                        onUpdateIsCompleted(i, isChecked)
                },
                onDelete = { i ->
                        onDeleteSubTask(i)

                },
                showCalendarForReminder = {
                    showCalendarForReminder(index)
                }

                ,
                showClockForReminder = {
                    showClockForReminder(index)
                }, clearDataOfSubTask = {
                    clearDataOfSubTask()
                }, clearTimeOfSubTask = {
                    clearTimeOfSubTask()
                }


            )
        }
    }
}
