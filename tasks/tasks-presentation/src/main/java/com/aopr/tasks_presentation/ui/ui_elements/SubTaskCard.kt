package com.aopr.tasks_presentation.ui.ui_elements

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp


@Composable
fun SubTaskCard(
    modifier: Modifier,
    tittle: String,
    onValueChange: (String) -> Unit,
    onCheckedChange: (Boolean) -> Unit,
    isCompleted: Boolean
) {

    Card(
        modifier = modifier
            .padding(vertical = 10.dp)
            .fillMaxWidth(0.7f)
    ) {
        Column(modifier = modifier.fillMaxSize(), verticalArrangement = Arrangement.SpaceEvenly) {
            Row(modifier = Modifier.fillMaxWidth()) {
            TextField(placeholder = {
                Text(text = stringResource(id = com.aopr.shared_domain.R.string.tittle))
            }, value = tittle, onValueChange = onValueChange, colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
            ))
           }
Checkbox(checked = isCompleted, onCheckedChange = onCheckedChange)
        }
    }


}