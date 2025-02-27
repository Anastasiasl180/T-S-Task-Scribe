package com.aopr.tasks_presentation.ui.ui_elements

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.aopr.shared_ui.cardsView.CircularCheckbox
import com.aopr.shared_ui.cardsView.cardViews
import com.aopr.tasks_domain.models.Task


@Composable
fun TaskCard(
    task: Task,
    tittle: String,
    description: String,
    isInSelectionMode: Boolean,
    goToTask: () -> Unit,
    onSelectTask: (Task, Boolean) -> Unit
) {

    val isSelected = remember(task) { mutableStateOf(false) }

    ElevatedCard(
        modifier = Modifier
            .height(220.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        shape = MaterialTheme.shapes.extraLarge,
    ) {

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(cardViews()), contentAlignment = Alignment.Center
        ) {

            Column(
                modifier = Modifier
                    .fillMaxHeight(0.9f)
                    .fillMaxWidth(0.9f)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.2f),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.End
                ) {

                    IconButton(
                        onClick = { goToTask() }, modifier = Modifier.size(40.dp),
                        colors = IconButtonDefaults.outlinedIconButtonColors(
                            Color.White.copy(
                                alpha = 0.2f
                            )
                        ),

                        ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Default.KeyboardArrowRight,
                            contentDescription = "ArrowRight",
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.3f),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = tittle)
                }
                Row(
                    modifier = Modifier
                        .fillMaxHeight(0.6f)
                        .fillMaxWidth()
                ) {
                    Text(
                        text = if (description.length > 50) description.take(50) + "..." else description,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxHeight(0.6f)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (isInSelectionMode) {
                        CircularCheckbox(checked = isSelected.value, onCheckedChange = { checked ->
                            isSelected.value = checked
                            onSelectTask(task, checked)
                        }, circleSize = 25.dp)
                    }
                }
            }

        }
    }

}

