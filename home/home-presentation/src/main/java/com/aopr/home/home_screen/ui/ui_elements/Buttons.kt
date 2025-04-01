package com.aopr.home.home_screen.ui.ui_elements

import androidx.compose.foundation.BorderStroke
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.aopr.home.R


@Composable
fun getNotesButtons(
    navigateToCreateNote: () -> Unit,
    navigateToAllNotes: () -> Unit
): Array<@Composable () -> Unit> {
    return arrayOf<@Composable () -> Unit>({
        Button(
            onClick = {
                navigateToAllNotes()
            },
            modifier = Modifier,
            colors = ButtonDefaults.buttonColors(Color.White.copy(alpha = 0.2f)),
            border = BorderStroke(width = 0.5.dp, color = Color.White.copy(alpha = 0.5f))
        ) {
            Text(text = stringResource(id = R.string.AllNotes), color = Color.White)
        }
    }, {
        Button(
            onClick = {
                navigateToCreateNote()
            }, modifier = Modifier,
            colors = ButtonDefaults.buttonColors(Color.White.copy(alpha = 0.2f)),
            border = BorderStroke(width = 0.5.dp, color = Color.White.copy(alpha = 0.5f))
        ) {
            Text(text = stringResource(id = R.string.NewNote), color = Color.White)
        }
    })
}

@Composable
internal fun getTasksButtons(
    navigateToAllTasks: () -> Unit,
    navigateToCreateTask: () -> Unit,
): Array<@Composable () -> Unit> {
    return arrayOf<@Composable () -> Unit>({
        Button(
            onClick = {
                navigateToAllTasks()
            }, modifier = Modifier,
            colors = ButtonDefaults.buttonColors(Color.White.copy(alpha = 0.2f)),
            border = BorderStroke(width = 0.5.dp, color = Color.White.copy(alpha = 0.5f))
        ) {
            Text(text = stringResource(id = R.string.AllTasks), color = Color.White)
        }
    }, {
        Button(
            onClick = {
                navigateToCreateTask()
            }, modifier = Modifier,
            colors = ButtonDefaults.buttonColors(Color.White.copy(alpha = 0.2f)),
            border = BorderStroke(width = 0.5.dp, color = Color.White.copy(alpha = 0.5f))
        ) {
            Text(text = stringResource(id = R.string.NewTask), color = Color.White)
        }
    })
}

@Composable
fun getBookMarksButtons(
    navigateToAllCategoriesOfBookmarks: () -> Unit,
    navigateToCreateBookmark: () -> Unit,
): Array<@Composable () -> Unit> {
    return arrayOf<@Composable () -> Unit>({
        Button(
            onClick = {
                navigateToAllCategoriesOfBookmarks()
            }, modifier = Modifier,
            colors = ButtonDefaults.buttonColors(Color.White.copy(alpha = 0.2f)),
            border = BorderStroke(width = 0.5.dp, color = Color.White.copy(alpha = 0.5f))
        ) {
            Text(text = stringResource(id = R.string.AllBookmarks), color = Color.White)
        }
    }, {
        Button(
            onClick = {
                navigateToCreateBookmark()
            }, modifier = Modifier,
            colors = ButtonDefaults.buttonColors(Color.White.copy(alpha = 0.2f)),
            border = BorderStroke(width = 0.5.dp, color = Color.White.copy(alpha = 0.5f))
        ) {
            Text(text = stringResource(id = R.string.NewBookmark), color = Color.White)
        }
    })
}

@Composable
fun getCalendarButton(navigateToCalendarScreen:()->Unit): Array<@Composable () -> Unit> {
    return arrayOf<@Composable () -> Unit>({
        Button(
            onClick = {
                navigateToCalendarScreen()
            }, modifier = Modifier,
            colors = ButtonDefaults.buttonColors(Color.White.copy(alpha = 0.2f)),
            border = BorderStroke(width = 0.5.dp, color = Color.White.copy(alpha = 0.5f))
        ) {
            Text(text = stringResource(id = R.string.Calendar), color = Color.White)
        }
    })
}