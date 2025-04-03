package com.aopr.home.home_screen.ui.ui_elements

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp


@Composable
fun CardsForThemes(modifier: Modifier, name: String, onChooseTheme: () -> Unit, colorsForCard: Brush) {

    Card(
        modifier = modifier
            .height(160.dp)
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