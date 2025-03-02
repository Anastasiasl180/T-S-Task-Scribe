package com.aopr.onboarding_presentation.ui.ui_elements

import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@Composable
fun NavigationButton(onDragComplete: () -> Unit, modifier: Modifier = Modifier) {
    var offsetX by remember { mutableFloatStateOf(0f) }
    val animatedOffsetX = remember { Animatable(0f) }
    val scope = rememberCoroutineScope()

    var cardWidth by remember { mutableFloatStateOf(0f) }

    Box(
        modifier = modifier
            .onGloballyPositioned { layoutCoordinates ->
                cardWidth = layoutCoordinates.size.width.toFloat()
            }
            .width(290.dp)
            .height(90.dp)
            .background(Color.DarkGray, shape = RoundedCornerShape(50.dp)),
        contentAlignment = Alignment.Center
    ) {
        val dragThreshold = cardWidth * 0.6f
        Row(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(0.94f),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Box(
                modifier = Modifier
                    .offset { IntOffset(animatedOffsetX.value.roundToInt(), 0) }
                    .height(75.dp)
                    .width(75.dp)
                    .background(Color.White, shape = CircleShape)
                    .pointerInput(Unit) {
                        detectDragGestures(
                            onDrag = { change, dragAmount ->
                                change.consume()
                                offsetX =
                                    (offsetX + dragAmount.x).coerceIn(0f, cardWidth - 90.dp.toPx())
                                scope.launch {
                                    animatedOffsetX.snapTo(offsetX)
                                }
                            },
                            onDragEnd = {
                                scope.launch {
                                    if (offsetX >= dragThreshold) {
                                        onDragComplete()
                                    }
                                    animatedOffsetX.animateTo(0f)
                                    offsetX = 0f
                                }
                            }
                        )
                    },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = "",
                    modifier = modifier
                        .size(25.dp),
                    tint = Color.DarkGray,
                )
            }
        }
    }
}

@Composable
fun BackButton(onClickUp: () -> Unit) {
    Box(
        modifier = Modifier
            .height(75.dp)
            .width(75.dp)
            .clickable { onClickUp() }
            .background(Color.Transparent, shape = CircleShape)
            .border(width = 2.dp, color = Color.DarkGray, shape = CircleShape),
        contentAlignment = Alignment.Center
    ) {

        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = "",
            modifier = Modifier
                .size(25.dp)
        )
    }
}

@Composable
fun HorizontalPageIndicator(
    currentPage: Int,
    totalPages: Int,
    activeColor: Color = Color.White,
    inactiveColor: Color = Color.Gray
) {
    Row(
        horizontalArrangement = Arrangement.Start,
        modifier = Modifier.fillMaxWidth()
    ) {
        repeat(totalPages) { index ->
            val isActive = index == currentPage
            val color = if (isActive) activeColor else inactiveColor

            val indicatorWidth = if (isActive) 20.dp else 8.dp
            val indicatorHeight = 4.dp

            Box(
                modifier = Modifier
                    .padding(horizontal = 4.dp)
                    .size(width = indicatorWidth, height = indicatorHeight)
                    .background(color, shape = RoundedCornerShape(50))
            )
        }
    }
}

