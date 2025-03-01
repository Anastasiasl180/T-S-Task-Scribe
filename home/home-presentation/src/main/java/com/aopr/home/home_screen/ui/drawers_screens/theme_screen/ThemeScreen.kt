package com.aopr.home.home_screen.ui.drawers_screens.theme_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aopr.home.R
import com.aopr.home.home_screen.ui.drawers_screens.ui_elements.CardsForThemes
import com.aopr.home.home_screen.ui.drawers_screens.ui_elements.Oval
import com.aopr.shared_domain.colors_for_theme.Themes
import com.aopr.shared_ui.cardsView.background
import com.aopr.shared_ui.cardsView.colorsForThemeCards
import com.aopr.shared_ui.top_app_bar.searchBarScrollBehaviour
import com.aopr.shared_ui.util.global_view_model.GlobalViewModel
import com.aopr.shared_ui.util.global_view_model.GlobalViewModelStoreOwner
import com.aopr.shared_ui.util.global_view_model.events.GlobalEvents
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ThemeChooserScreen() {
    val globalViewModel =
        koinViewModel<GlobalViewModel>(viewModelStoreOwner = GlobalViewModelStoreOwner)

    val backgroundTheme = background()
    val colorsForCards = colorsForThemeCards()
    val brushCircle = Brush.radialGradient(
        0.0f to MaterialTheme.colorScheme.onPrimaryContainer,
        0.2f to MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.7f),
        0.4f to MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.4f),
        0.6f to MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.2f),
        0.8f to MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.08f),
        0.95f to MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.01f),
        1.0f to MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.0f)
    )

    val topAppBarDefaults = searchBarScrollBehaviour()
    Scaffold(
        topBar = {
            TopAppBar(colors = TopAppBarDefaults.mediumTopAppBarColors(
                containerColor = Color.Transparent,
                scrolledContainerColor = Color.Transparent
            ), scrollBehavior = topAppBarDefaults,
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
                title = { /*TODO*/
                })
        }

    ) { _ ->

        val themesEntries = Themes.entries


        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = backgroundTheme,
                ), contentAlignment = Alignment.Center
        ) {
            Row(modifier = Modifier.fillMaxWidth()) {
                Card(
                    modifier = Modifier
                        .fillMaxSize(0.9f)
                        .offset(x = -160.dp)
                        .clip(Oval())
                        .graphicsLayer { alpha = 0.5f }
                        .background(brush = brushCircle),
                    colors = CardDefaults.cardColors(Color.Transparent)

                ) {

                }
            }
        }
        Column(modifier = Modifier.fillMaxSize()) {

            Box(
                modifier = Modifier
                    .fillMaxHeight(0.2f)
                    .fillMaxWidth(0.6f), contentAlignment = Alignment.BottomCenter
            ) {
                Text(
                    text = stringResource(
                        R.string.choose_theme
                    ),
                    fontSize = 30.sp,
                    fontFamily = FontFamily(
                        Font(com.aopr.shared_ui.R.font.open_sans_light)
                    ),
                )
            }
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Box(
                    modifier = Modifier
                        .fillMaxHeight(0.81f)
                        .clip(shape = RoundedCornerShape(20.dp))
                        .fillMaxWidth(0.95f),
                    contentAlignment = Alignment.Center
                ) {
                    LazyColumn(
                        modifier = Modifier,
                        verticalArrangement = Arrangement.spacedBy(5.dp)
                    ) {

                        items(themesEntries.zip(colorsForCards)) { (theme, brush) ->

                            CardsForThemes(
                                modifier = Modifier,
                                colorsForCard = brush,
                                name = theme.name,
                                onChooseTheme = {
                                    globalViewModel.onEvent(GlobalEvents.ChosenTheme(theme))

                                })

                        }
                    }


                }
            }

        }
    }

}
