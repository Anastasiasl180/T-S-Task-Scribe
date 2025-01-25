package com.aopr.tasks_presentation.ui.ui_elements

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.animateFloatAsState
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxColors
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.aopr.notes_presentation.R
import com.aopr.tasks_domain.models.Subtasks
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
    clearDataOfSubTask: (Int) -> Unit,
    clearTimeOfSubTask: (Int) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val rotationState by animateFloatAsState(targetValue = if (expanded) 180f else 0f, label = "")
    Card(
        modifier = Modifier
            .height(if (expanded) 300.dp else 100.dp)
            .animateContentSize(
                animationSpec = TweenSpec(
                    durationMillis = 300,
                    easing = LinearOutSlowInEasing
                )
            )
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.LightGray.copy(alpha = 0.2f))
    ) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {

            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(0.95f),
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(if (expanded) 0.1f else 0.3f), horizontalArrangement = Arrangement.End
                ) {

                    IconButton(onClick = { expanded = !expanded }, modifier = Modifier.rotate(rotationState)) {
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowUp,
                            contentDescription = ""
                        )
                    }
                }
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Start) {

                    TextField(
                        placeholder = {
                            Text(text = stringResource(id = R.string.enterSubtask))
                        },
                        value = tittle,
                        onValueChange = { newValue -> onValueDescriptionChange(index, newValue) },
                        modifier = Modifier,
                        singleLine = true,
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent
                        )
                    )
                }

                if (expanded) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(onClick = { showCalendarForReminder(index) }) {
                            Icon(
                                imageVector = Icons.Default.DateRange,
                                contentDescription = "calendar",
                            )
                        }
                        Text(
                            text = data?.format(
                                DateTimeFormatter.ofPattern(
                                    "dd MMM yyyy"
                                )
                            )
                                ?: stringResource(id = R.string.selectDateForReminderOfSubtask),
                        )

                        TextButton(
                            onClick = { clearDataOfSubTask(index) }
                        ) {
                            Text(
                                text = stringResource(id = R.string.cleanField),
                                color = Color.White
                            )
                        }


                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(onClick = { showClockForReminder(index) }) {
                            Icon(
                                imageVector = Icons.Default.Notifications,
                                contentDescription = "reminderButton"
                            )
                        }
                        Text(
                            text = time?.format(DateTimeFormatter.ofPattern("HH:mm"))
                                ?: stringResource(id = R.string.selectTimeForReminderOfSubtask),
                        )


                        TextButton(
                            onClick = { clearTimeOfSubTask(index) }
                        ) {
                            Text(
                                text = stringResource(id = R.string.cleanField),
                                color = Color.White
                            )
                        }


                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(0.25f),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {


                            Text(stringResource(id = R.string.doneOrNo))
                            Checkbox(colors = CheckboxColors(
                                checkedCheckmarkColor = Color.White,
                                uncheckedCheckmarkColor = Color.Gray,
                                checkedBorderColor = Color.White,
                                checkedBoxColor = Color.Transparent,
                                uncheckedBoxColor = Color.Transparent,
                                disabledBorderColor = Color.White,
                                disabledUncheckedBoxColor = Color.White,
                                uncheckedBorderColor = Color.White,
                                disabledUncheckedBorderColor = Color.White,
                                disabledCheckedBoxColor = Color.White,
                                disabledIndeterminateBorderColor = Color.White,
                                disabledIndeterminateBoxColor = Color.White
                            ), checked = isCompleted, onCheckedChange = { isChecked ->
                                onIsCompletedChange(index, isChecked)
                            })
                        }
                        TextButton(onClick = { onDelete(index) }) {
                            Text(text = stringResource(id = R.string.delete), color = Color.Gray)
                        }
                    }
                }

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
    clearDataOfSubTask: (Int) -> Unit,
    clearTimeOfSubTask: (Int) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 15.dp),
        verticalArrangement = Arrangement.spacedBy(40.dp)
    ) {
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
                },
                showClockForReminder = {
                    showClockForReminder(index)
                }, clearDataOfSubTask = { clearDataOfSubTask(index) }, clearTimeOfSubTask = {
                    clearTimeOfSubTask(index)
                }


            )
        }
        Spacer(modifier = Modifier.height(5.dp))

    }
}
