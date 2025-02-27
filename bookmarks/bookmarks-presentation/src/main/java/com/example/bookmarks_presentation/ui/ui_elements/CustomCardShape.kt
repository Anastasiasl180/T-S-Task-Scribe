package com.example.bookmarks_presentation.ui.ui_elements


import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aopr.shared_ui.cardsView.CircularCheckbox
import com.aopr.shared_ui.cardsView.bookmarkCardsViews
import com.example.bookmarks_domain.models.Bookmark
import com.example.bookmarks_presentation.R


class SecondShape(
    private val cornerRadiusDp: Float = 8f
) : Shape {
    fun createPath(size: Size, density: Density): Path = with(density) {
        val cornerRadius = cornerRadiusDp.dp.toPx()

        val width = size.width * 1f
        val height = size.height * 1f
        val offsetX = size.width * 0.0f
        val offsetY = size.height * 0.0f

        return Path().apply {


            addRoundRect(
                roundRect = RoundRect(
                    offsetX,
                    offsetY,
                    offsetX + width,
                    offsetY + height, cornerRadius = CornerRadius(cornerRadius)
                ),
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

class CustomShape(
    private val verticalLineEndYRatio: Float = 0.6f,
    private val lineIntoCardLengthRatio: Float = 0.19f,
    private val cornerRadiusDp: Float = 16f
) : Shape {

    fun createPath(size: Size, density: Density): Path = with(density) {
        val width = size.width
        val height = size.height

        val verticalLineEndY = height * verticalLineEndYRatio
        val lineIntoCardLength = width * lineIntoCardLengthRatio

        val cornerRadius = cornerRadiusDp.dp.toPx()

        return Path().apply {
            // Start at the top-left corner
            moveTo(0f + cornerRadius, 0f)

            // Top edge with top-right corner
            lineTo(width - cornerRadius, 0f)
            quadraticTo(width, 0f, width, 0f + cornerRadius)

            // Right edge down to verticalLineEndY minus cornerRadius
            lineTo(width, verticalLineEndY - cornerRadius)
            quadraticTo(width, verticalLineEndY, width - cornerRadius, verticalLineEndY)

            // Horizontal line into the card (lineIntoCardLength)
            lineTo(width - lineIntoCardLength + cornerRadius, verticalLineEndY)
            quadraticTo(
                width - lineIntoCardLength,
                verticalLineEndY,
                width - lineIntoCardLength,
                verticalLineEndY + cornerRadius
            )

            // Down to the bottom edge minus cornerRadius
            lineTo(width - lineIntoCardLength, height - cornerRadius)
            quadraticTo(
                width - lineIntoCardLength,
                height,
                width - lineIntoCardLength - cornerRadius, height
            )

            // Bottom edge to the bottom-left corner plus cornerRadius
            lineTo(0f + cornerRadius, height)
            quadraticTo(0f, height, 0f, height - cornerRadius)

            // Left edge up to the top-left corner minus cornerRadius
            lineTo(0f, 0f + cornerRadius)
            quadraticTo(0f, 0f, 0f + cornerRadius, 0f)

            // Close the path
            close()
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


@Composable
fun CustomCard(
    modifier: Modifier = Modifier,
    bookmark: Bookmark,
    tittleOfBookmark: String,
    navigateToBookmark: () -> Unit,
    deleteBookmark: () -> Unit,
    isInSelectionMode: Boolean,
    isOnSelectedBookmark: (Bookmark, Boolean) -> Unit

) {
    val density = LocalDensity.current
    val customShape = CustomShape()
    val secondShape = SecondShape()
    val isSelected = remember(bookmark) { mutableStateOf(false) }

    val cardViews = bookmarkCardsViews()
    Box(modifier = modifier) {

        Card(
            modifier = Modifier
                .fillMaxSize()
                .clip(customShape)
                .clickable(onClick = { navigateToBookmark() }),
            shape = customShape,
            colors = CardDefaults.cardColors(containerColor = if (isSelected.value) Color.Transparent else Color.Gray),
            elevation = CardDefaults.cardElevation(20.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(customShape)
                    .background(cardViews)
            ) {
                if (isInSelectionMode) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(0.95f)
                            .fillMaxHeight(0.35f),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.End
                    ) {
                        CircularCheckbox(checked = isSelected.value, onCheckedChange = { checked ->
                            isSelected.value = checked
                            isOnSelectedBookmark(bookmark, checked)
                        }, circleSize = 25.dp)

                    }
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.35f),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {


                    Text(
                        text = tittleOfBookmark,
                        fontSize = 30.sp,
                        color = Color.White,
                        fontFamily = FontFamily(
                            Font(com.aopr.shared_ui.R.font.open_sans_light)
                        ),
                        modifier = Modifier.padding(start = 20.dp)

                    )
                }


            }
        }

        Box(
            modifier = Modifier
                .fillMaxSize(), contentAlignment = Alignment.BottomEnd

        ) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomEnd) {
                Column(
                    modifier = Modifier
                        .fillMaxHeight(0.35f)
                        .fillMaxWidth(0.15f),
                    verticalArrangement = Arrangement.SpaceEvenly,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {


                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "",
                        modifier = Modifier
                            .size(20.dp)
                            .clickable { deleteBookmark() },
                        tint = Color.White.copy(alpha = 0.7f)
                    )
                    Icon(
                        painter = painterResource(R.drawable.follow_the_link_icon),
                        contentDescription = "",
                        modifier = Modifier
                            .size(20.dp)
                            .clickable { navigateToBookmark() },
                        tint = Color.White.copy(alpha = 0.7f)
                    )
                }
            }


            Canvas(
                modifier = Modifier
                    .fillMaxHeight(0.35f)
                    .fillMaxWidth(0.15f)
            ) {
                val path = secondShape.createPath(size, density)
                drawPath(
                    path = path,
                    color = Color.Transparent
                )

                drawPath(
                    path = path,
                    color = Color.White.copy(alpha = 0.5f),
                    style = Stroke(width = 2.dp.toPx())
                )


            }
        }

    }
}
