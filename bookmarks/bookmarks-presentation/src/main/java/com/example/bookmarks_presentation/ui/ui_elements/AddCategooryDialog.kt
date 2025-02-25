package com.example.bookmarks_presentation.ui.ui_elements

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.aopr.shared_ui.cardsView.gradientOfDialogs
import com.example.bookmarks_presentation.R

@Composable
fun AddCategoryDialog(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    updateTittle: (String) -> Unit,
    tittle: String
) {

    val gradient = gradientOfDialogs()
    Dialog(onDismissRequest = onDismiss) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(375.dp)
                .padding(16.dp)
                .clip(shape = RoundedCornerShape(30.dp))
        ) {
            ElevatedCard(
                modifier = Modifier
                    .fillMaxSize()
                    .border(
                        brush = gradient, width = 3.dp, shape = RoundedCornerShape(30.dp)
                    ),
                shape = MaterialTheme.shapes.extraSmall,
                colors = CardDefaults.cardColors(
                    containerColor = colorResource(com.aopr.shared_ui.R.color.backgroundOfDialogs)
                )
            ) {

                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.SpaceEvenly,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {

                    Text(
                        text = stringResource(
                            R.string.add_category
                        ),
                        modifier = Modifier.padding(16.dp),
                        fontFamily = FontFamily(Font(com.aopr.shared_ui.R.font.open_sans_light))

                    )

                    OutlinedTextField(
                        shape = MaterialTheme.shapes.medium,
                        modifier = Modifier
                            .fillMaxWidth(0.9f)
                            .height(60.dp),
                        placeholder = {
                            Text(
                                text = stringResource(com.aopr.shared_ui.R.string.tittle),
                                fontFamily = FontFamily(Font(com.aopr.shared_ui.R.font.open_sans_light))
                            )
                        },
                        value = tittle,
                        onValueChange = { updateTittle(it) },
                        colors = TextFieldDefaults.colors(
                            focusedIndicatorColor = Color.Black,
                            unfocusedIndicatorColor = Color.Black,
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,


                            ),

                        )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                    ) {
                        TextButton(
                            onClick = onDismiss,
                            modifier = Modifier.padding(8.dp),
                        ) {
                            Text(
                                stringResource(com.aopr.shared_ui.R.string.dismiss),
                                color = Color.White,
                                fontFamily = FontFamily(Font(com.aopr.shared_ui.R.font.open_sans_light))
                            )
                        }
                        TextButton(
                            onClick = onConfirm,
                            modifier = Modifier.padding(8.dp),
                        ) {
                            Text(
                                stringResource(com.aopr.shared_ui.R.string.confirm),
                                color = Color.White,
                                fontFamily = FontFamily(Font(com.aopr.shared_ui.R.font.open_sans_light))
                            )
                        }
                    }

                }
            }
        }
    }

}