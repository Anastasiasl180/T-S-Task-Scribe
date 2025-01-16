package com.aopr.home.home_screen.drawers_screens.theme_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aopr.shared_domain.colors_for_theme.Themes
import com.aopr.shared_ui.MainViewModel
import com.aopr.shared_ui.cardsView.background
import com.aopr.shared_ui.cardsView.colorsForThemeCards
import com.aopr.shared_ui.util.MainViewModelStoreOwner
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ThemeChooserScreen() {
    val mainViewModel = koinViewModel<MainViewModel>(viewModelStoreOwner = MainViewModelStoreOwner)

    val brush1 = background()
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
    Scaffold(topBar = {
        TopAppBar( colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent,
        ), title = {},
            navigationIcon = {
                IconButton(onClick = {  }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "", tint = Color.White
                    )
                }
            })
    }) { _ ->

        val themesEntries = Themes.entries


        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = brush1,
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
                .fillMaxHeight(0.15f)
                .fillMaxWidth(0.5f), contentAlignment = Alignment.BottomCenter
        ) {
            Text(text = "Choose theme", fontSize = 20.sp)
        }
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Box(
                modifier = Modifier
                    .fillMaxHeight(0.81f)
                    .clip(shape = RoundedCornerShape(20.dp))
                    .fillMaxWidth(0.95f),
                contentAlignment = Alignment.Center
            ) {
                LazyColumn(modifier = Modifier, verticalArrangement = Arrangement.spacedBy(16.dp)) {

                    items(themesEntries.zip(colorsForCards)) { (theme, brush) ->

                        CardsForThemes(modifier = Modifier, colorsForCard = brush, name = theme.name, onChooseTheme = {
                            mainViewModel.onEvent(MainViewModel.MainEvent.ChosenTheme(theme))

                        })

                    }
                }


            }
        }

    }
    }

}

@Composable
fun CardsForThemes(modifier: Modifier,name: String, onChooseTheme: () -> Unit,colorsForCard:Brush) {

    Card(
        modifier = modifier
            .height(140.dp)
            .fillMaxWidth()
            .clickable { onChooseTheme() },
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(Color.Transparent)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = colorsForCard,
                    shape = RoundedCornerShape(
                        20.dp
                    )
                ), contentAlignment = Alignment.Center
        ) {
            Text(text = name)
        }

    }
}

class Oval(
) : Shape {
    fun createPath(size: Size, density: Density): Path = with(density) {

        val width = size.width * 1f
        val height = size.height * 1f
        val offsetX = size.width * 0.0f
        val offsetY = size.height * 0.0f

        return Path().apply {

            addOval(
                oval = Rect(
                    offset = Offset(offsetX, offsetY),
                    size = Size(width, height)
                )
            )

        }
    }


    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val path = createPath(size, density)
        return Outline.Generic(path)
    }
}


/* .graphicsLayer { alpha = 0.99f }
            .drawWithContent {
                val colors = listOf(
                    Color.Black,
                    Color.Transparent

                )
                drawContent()
                drawRect(

                    brush = Brush.verticalGradient(colors),
                    blendMode = BlendMode.DstIn,

                    )
            }*/
