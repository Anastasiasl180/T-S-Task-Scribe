package com.aopr.home.home_screen.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.aopr.home.R
import com.aopr.shared_domain.colors_for_theme.Themes
import com.aopr.shared_ui.cardsView.background
import com.radusalagean.infobarcompose.InfoBar
import com.radusalagean.infobarcompose.InfoBarMessage
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable

internal fun BottomSheetContent(
    tittle: String,
    description: String,
    onDismiss: () -> Unit,
    saveNote: () -> Unit,
    updateTittle: (String) -> Unit,
    updateDescription: (String) -> Unit,
    infoBarMessage:InfoBarMessage?
) {


    val brush = background()
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val scope = rememberCoroutineScope()
    ModalBottomSheet(
        onDismissRequest = { onDismiss() },
        sheetState = sheetState,
        modifier = Modifier
            .consumeWindowInsets(WindowInsets.ime)
    ) {
        Box(
            modifier = Modifier
                .consumeWindowInsets(WindowInsets.ime)
                .fillMaxSize(), contentAlignment = Alignment.Center
        ) {

            Column(
                modifier = Modifier
                    .consumeWindowInsets(WindowInsets.ime)
                    .fillMaxHeight()
                    .fillMaxWidth(0.95f)

            ) {
                Row(
                    modifier = Modifier
                        .consumeWindowInsets(WindowInsets.ime)
                        .fillMaxWidth()
                        .fillMaxHeight(0.05f),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    TextButton(onClick = { onDismiss() }) {
                        Text(text = stringResource(id = R.string.Cancel), color = Color.White)
                    }

                    Text(text = stringResource(id = R.string.NewNote))
                    TextButton(onClick = { saveNote() }) {
                    Text(text = stringResource(id = R.string.Done), color = Color.White)}
                }
                Row(
                    modifier = Modifier
                        .consumeWindowInsets(WindowInsets.ime)
                        .fillMaxWidth()
                        .fillMaxHeight(0.2f)
                ) {
                    Column(
                        modifier = Modifier
                            .consumeWindowInsets(WindowInsets.ime)
                            .fillMaxSize(),
                        verticalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Text(text = stringResource(id = R.string.Tittle))
                        TextField(
                            shape = MaterialTheme.shapes.medium,
                            modifier = Modifier
                                //  .border(width = 3.dp, brush =text , shape = MaterialTheme.shapes.medium)
                                .fillMaxWidth()
                                .height(60.dp),
                            placeholder = {
                                Text(text = stringResource(id = R.string.Tittle))
                            },
                            value = tittle,
                            onValueChange = updateTittle,
                            colors = TextFieldDefaults.colors(
                                focusedIndicatorColor =Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent
                            ),

                            )
                    }

                }
                Column(
                    modifier = Modifier
                        .consumeWindowInsets(WindowInsets.ime)
                        .fillMaxSize()
                ) {
                    Text(text = stringResource(id = R.string.Description))
                    Spacer(modifier = Modifier.height(10.dp))
                    Row(
                        modifier = Modifier
                            .consumeWindowInsets(WindowInsets.ime)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {

                        TextField(
                            value = description,
                            shape = MaterialTheme.shapes.medium,
                            placeholder = {
                                Text(text = stringResource(id = R.string.Description))
                            },
                            onValueChange = updateDescription,
                            colors = TextFieldDefaults.colors(
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .onFocusChanged { focusState ->
                                    if (focusState.isFocused) {
                                        scope.launch {
                                            sheetState.expand()
                                        }
                                    }
                                }
                                .height(150.dp),
                            maxLines = 20,
                        )
                    }
                }


            }
          InfoBar(offeredMessage = infoBarMessage) {

          }
        }
    }
}