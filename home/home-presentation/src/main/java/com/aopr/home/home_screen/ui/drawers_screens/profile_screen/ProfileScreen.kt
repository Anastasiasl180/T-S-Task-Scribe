package com.aopr.home.home_screen.ui.drawers_screens.profile_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.aopr.home.home_screen.ui.ui_elements.Oval
import com.aopr.shared_ui.cardsView.background
import com.aopr.shared_ui.util.global_view_model.GlobalViewModel
import com.aopr.shared_ui.util.global_view_model.GlobalViewModelStoreOwner
import com.aopr.shared_ui.util.global_view_model.events.GlobalEvents
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen() {

    val globalViewModel =
        koinViewModel<GlobalViewModel>(viewModelStoreOwner = GlobalViewModelStoreOwner)
    val backgroundTheme = background()
    Scaffold(topBar = {
        TopAppBar(colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent),
            navigationIcon = {
                IconButton(
                    onClick = { globalViewModel.onEvent(GlobalEvents.NavigateBack) },
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(Color.DarkGray.copy(alpha = 0.6f))
                        .size(50.dp)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Default.KeyboardArrowLeft,
                        contentDescription = "",
                        tint = Color.White, modifier = Modifier.size(20.dp)
                    )
                }
            },
            title = { /*TODO*/ })
    }

    ) { _ ->


        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(backgroundTheme), contentAlignment = Alignment.BottomCenter
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight(0.8f)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(20.dp))
                    .background(
                        colorResource(
                            com.aopr.shared_ui.R.color.backgroundOfDialogs
                        )
                    ), contentAlignment = Alignment.Center

            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize(0.8f)
                        .clip(RoundedCornerShape(20.dp)), contentAlignment = Alignment.Center
                ) {

                    Column(
                        modifier = Modifier
                            .fillMaxHeight(0.9f)
                            .fillMaxWidth(),
                        verticalArrangement = Arrangement.SpaceEvenly,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        CustomCard(
                            userName = "name",
                            password = "password",
                            gmail = "gmail"
                        ).forEach { card ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth(0.9f)
                                    .fillMaxHeight(0.2f)
                                    .weight(1f)
                            ) {
                                card()
                            }
                        }


                    }

                }

            }

        }


        Box(
            modifier = Modifier
                .fillMaxHeight(0.4f)
                .fillMaxWidth(), contentAlignment = Alignment.CenterEnd
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.4f)
                    .fillMaxHeight(0.3f),
                contentAlignment = Alignment.Center
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()

                ) {

                    //  if (bitMaoImage == null) {
                    Icon(
                        imageVector = Icons.Default.AccountCircle,
                        contentDescription = "",
                        modifier = Modifier.fillMaxSize()
                    )

                    /*   } else {
                        AsyncImage(
                            model = bitMaoImage,
                            contentDescription = "",
                            modifier = Modifier.fillMaxSize()
                        )
                    }*/
                }
            }
        }


    }
}

@Composable
fun CustomCard(userName: String, password: String, gmail: String): Array<@Composable () -> Unit> {
    return arrayOf<@Composable () -> Unit>(
        {
            CustomCard(userName)
        }, {

            CustomCard(password)

        }, {
            CustomCard(gmail)
        }

    )

}

@Composable
fun CustomCard(data: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.4f)
            .border(width = 1.dp, color = Color.LightGray.copy(alpha = 0.1f),    shape = MaterialTheme.shapes.medium).clip(
                shape = MaterialTheme.shapes.medium
            ),
        colors = CardDefaults.cardColors(containerColor = Color.LightGray.copy(alpha = 0.1f))
    ) {

        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {

            Row(
                modifier = Modifier
                    .fillMaxHeight(0.9f)
                    .fillMaxWidth(0.9f),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = data)
                IconButton(onClick = {

                }) {

                    Icon(imageVector = Icons.Default.KeyboardArrowRight, contentDescription = "")

                }

            }

        }

    }

}