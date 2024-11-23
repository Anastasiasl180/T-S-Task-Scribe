package com.aopr.tasks_presentation.ui.ui_elements

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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

@Composable
fun SubTaskCard(
    index: Int,
    tittle: String,
    onValueChange: (Int, String) -> Unit,
    onCheckedChange: (Int, Boolean) -> Unit,
    onDelete: (Int) -> Unit,
    isCompleted: Boolean
) {
    Card(
        modifier = Modifier
            .padding(vertical = 10.dp)
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,

        ) {
            TextField(
                placeholder = {
                    Text(text = "Enter Subtask")
                },
                value = tittle,
                onValueChange = { newValue -> onValueChange(index, newValue) },
                modifier = Modifier.weight(1f),
                singleLine = true,
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                )
            )
            Checkbox(checked = isCompleted, onCheckedChange = { isChecked ->
                onCheckedChange(index, isChecked)
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
    onAddSubTask: () -> Unit,
    onUpdateSubTask: (Int, String, Boolean) -> Unit,
    onDeleteSubTask: (Int) -> Unit
) {
    Column {
        subtasks.forEachIndexed { index, subtask ->
            SubTaskCard(
                index = index,
                tittle = subtask.description,
                isCompleted = subtask.isCompleted,
                onValueChange = { i, newValue -> onUpdateSubTask(i, newValue, subtasks[i].isCompleted) },
                onCheckedChange = { i, isChecked -> onUpdateSubTask(i, subtasks[i].description, isChecked) },
                onDelete = onDeleteSubTask
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = onAddSubTask, modifier = Modifier.fillMaxWidth()) {
            Text(text = "Add Subtask")
        }
    }
}
