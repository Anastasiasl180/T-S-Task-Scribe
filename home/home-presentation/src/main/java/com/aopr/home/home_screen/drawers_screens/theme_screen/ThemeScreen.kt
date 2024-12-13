package com.aopr.home.home_screen.drawers_screens.theme_screen

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.LinearGradient
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.PaintingStyle.Companion.Stroke
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.aopr.shared_domain.colors_for_theme.Themes
import com.aopr.shared_ui.MainViewModel
import com.aopr.shared_ui.util.MainViewModelStoreOwner
import org.koin.androidx.compose.koinViewModel

@Composable
fun ThemeChooserScreen() {
    val mainViewModel = koinViewModel<MainViewModel>(viewModelStoreOwner = MainViewModelStoreOwner)

    val weightScreen = LocalConfiguration.current.screenHeightDp

    val brush = Brush.linearGradient(
        listOf(
            MaterialTheme.colorScheme.primary,
            MaterialTheme.colorScheme.secondary,
            MaterialTheme.colorScheme.tertiary,
            MaterialTheme.colorScheme.onPrimary,
            MaterialTheme.colorScheme.primaryContainer,
        ), start = Offset.Zero,
        end = Offset.Infinite
    )
    val brushCircle = Brush.radialGradient(
        0.0f to MaterialTheme.colorScheme.onPrimaryContainer,
        0.2f to MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.8f),
        0.5f to MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.4f),
        0.5f to MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.4f),
        0.6f to MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.3f),
        0.7f to MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.2f),
        0.8f to MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.1f),
        0.9f to MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.05f),
        1.0f to MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.0f),
    )
    Scaffold { _ ->

        val themesEntries = Themes.entries
        val chosenTheme by mainViewModel.chosenTheme.collectAsState()


        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = brush,
                ), contentAlignment = Alignment.Center
        ) {
            Row(modifier = Modifier.fillMaxWidth()) {
                Card(
                    modifier = Modifier
                        .fillMaxSize(0.9f)
                        .offset(x = -160.dp)
                        .clip(Oval())
                        .graphicsLayer { alpha = 0.5f }
                        .background(brush = brushCircle), colors = CardDefaults.cardColors(Color.Transparent)

                ) {

                }
            }
            Column(modifier = Modifier.fillMaxSize(0.5f)) {

                themesEntries.forEach { theme ->

                    Row(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Text(theme.name, color = Color.Red)
                        RadioButton(selected = (theme == chosenTheme), onClick = {
                            mainViewModel.onEvent(MainViewModel.MainEvent.ChosenTheme(theme))
                        })

                    }


                }

            }

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
