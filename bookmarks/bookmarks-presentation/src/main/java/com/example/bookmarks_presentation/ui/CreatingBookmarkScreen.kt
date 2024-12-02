package com.example.bookmarks_presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.bookmarks_presentation.events.creating_bookmark_events.CreatingBookmarkEvents
import com.example.bookmarks_presentation.view_models.CreatingBookmarkViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun CreatingBookMarkScreen() {
    val viewModel = koinViewModel<CreatingBookmarkViewModel>()
    val tittleOfBookmark by viewModel.tittle
    Box(
        modifier = Modifier.fillMaxSize().background(Color.Cyan),
        contentAlignment = Alignment.Center
    ) {
        Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center) {
        TextField(
            shape = MaterialTheme.shapes.medium,
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp),
            placeholder = {
                Text(text ="tittle")
            },
            value = tittleOfBookmark,
            onValueChange = {
                viewModel.oEvent(
                    CreatingBookmarkEvents.UpdateTittleOfBookmark(
                        it
                    )
                )
            },
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),

            )
            Button(onClick = {
                viewModel.oEvent(CreatingBookmarkEvents.SaveBookmark)
            }){
                Text("save")
            }
    }
    }
}