package com.aopr.home.home_screen.ui.ui_elements

import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aopr.home.R
import com.aopr.shared_ui.cardsView.cardViews
import com.aopr.tasks_domain.models.Task
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


@Composable
fun CalendarCard(tasks: List<Task>) {
    val todayDate = remember {
        SimpleDateFormat("dd MMMM yyyy", Locale.getDefault()).format(Date())
    }
    Card(
        modifier = Modifier
            .fillMaxHeight(0.3f)
            .fillMaxWidth()
            .clip(RoundedCornerShape(30.dp)),
        colors = CardDefaults.cardColors(containerColor = Color.LightGray.copy(alpha = 0.2f))
    ) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(0.95f)
                    .fillMaxHeight(0.9f),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth(0.5f)
                        .clip(RoundedCornerShape(30.dp))
                        .background(cardViews())
                ) {
                    Card(
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(RoundedCornerShape(30.dp)),
                        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
                    ) {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.SpaceEvenly,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {

                            Text(
                                text = todayDate,
                                fontSize = 15.sp, fontWeight = FontWeight.Bold,
                                fontFamily = FontFamily(
                                    Font(com.aopr.shared_ui.R.font.open_sans_light)
                                ),
                                color = Color.White
                            )
                            Text(
                                text = stringResource(id = R.string.tasks_label, tasks.size), fontSize = 15.sp,
                                fontFamily = FontFamily(
                                    Font(com.aopr.shared_ui.R.font.open_sans_light)
                                )
                            )

                        }
                    }
                }
                Box(
                    modifier = Modifier
                        .fillMaxSize(), contentAlignment = Alignment.Center
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize(0.95f),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(0.95f)
                                .fillMaxHeight(0.2f),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = stringResource(R.string.Upcoming), fontFamily = FontFamily(
                                    Font(com.aopr.shared_ui.R.font.open_sans_light)
                                ), fontSize = 20.sp
                            )
                        }
                        if (tasks.isNotEmpty()) {
                            Spacer(modifier = Modifier.height(15.dp))
                            LazyColumn (
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(start = 10.dp), verticalArrangement = Arrangement.spacedBy(10.dp)
                            ) {



                                items(tasks){ it ->
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .weight(1f),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(5.dp)
                                    ) {
                                        Box(modifier = Modifier
                                            .fillMaxHeight()
                                            .fillMaxWidth(0.1f), contentAlignment = Alignment.CenterStart) {
                                            VerticalDivider(
                                                thickness = 3.dp,
                                                color = Color.White,
                                                modifier = Modifier
                                                    .height(30.dp)
                                                    .clip(
                                                        RoundedCornerShape(20.dp)
                                                    )
                                            )
                                        }
                                        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.CenterStart) {
                                            Text(
                                                text = if (it.tittle.length > 10) it.tittle.take(10) + "..." else it.tittle,
                                                fontFamily = FontFamily(
                                                    Font(com.aopr.shared_ui.R.font.open_sans_light),
                                                ), fontSize = 20.sp, color = Color.White

                                            )
                                        }
                                    }
                                    Spacer(modifier = Modifier.height(10.dp))
                                }
                            }
                        } else {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    stringResource(R.string.No_task_for_today), fontFamily = FontFamily(
                                        Font(com.aopr.shared_ui.R.font.open_sans_light),
                                    ), fontSize = 15.sp
                                )
                            }
                        }
                    }
                }

            }


        }

    }
}