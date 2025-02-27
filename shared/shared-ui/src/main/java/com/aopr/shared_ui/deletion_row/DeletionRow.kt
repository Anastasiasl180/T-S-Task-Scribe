package com.aopr.shared_ui.deletion_row

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily

@Composable
fun DeletionRow(isInSelectionModeForDelete:Boolean,turnOnSelectionMode:()->Unit,deleteChosenItems:()->Unit,) {
    if (isInSelectionModeForDelete) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            TextButton(onClick = { turnOnSelectionMode()}) {
                Text(
                    text = stringResource(id = com.aopr.shared_ui.R.string.cancel),
                    color = Color.White,
                    fontFamily = FontFamily(Font(com.aopr.shared_ui.R.font.open_sans_light))
                )
            }

            TextButton(onClick = { deleteChosenItems() }) {
                Text(
                    text = stringResource(id = com.aopr.shared_ui.R.string.delete),
                    color = Color.White,
                    fontFamily = FontFamily(Font(com.aopr.shared_ui.R.font.open_sans_light))
                )
            }
        }
    } else {
        Text(
            stringResource(com.aopr.shared_ui.R.string.chooseToDelete),
            fontFamily = FontFamily(Font(com.aopr.shared_ui.R.font.open_sans_light)),
            modifier = Modifier.clickable { turnOnSelectionMode() }
        )
    }
}