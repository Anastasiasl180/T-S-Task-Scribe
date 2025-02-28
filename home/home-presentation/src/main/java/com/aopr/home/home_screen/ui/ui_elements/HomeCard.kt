package com.aopr.home.home_screen.ui.ui_elements

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.toFontFamily
import androidx.compose.ui.unit.dp
import com.aopr.shared_ui.cardsView.cardViews
import com.aopr.shared_ui.theme.cabinFont

@Composable
internal fun HomeCard(
    isBlurred: Boolean, cardText: String, onCardClick: () -> Unit,
    vararg buttons: @Composable () -> Unit
) {

    val animatedAlpha by animateFloatAsState(
        targetValue = if (isBlurred) 0.5f else 1f,
        animationSpec = tween(durationMillis = 500), label = ""
    )
    val animatedBlur by animateDpAsState(
        targetValue = if (isBlurred) 10.dp else 0.dp,
        animationSpec = tween(durationMillis = 500), label = ""
    )

    ElevatedCard(
        modifier = Modifier
            .height(200.dp)
            .blur(
                radius = animatedBlur,
                edgeTreatment = BlurredEdgeTreatment(
                    MaterialTheme.shapes.extraLarge
                )
            )
            .border(
                border = BorderStroke(0.5.dp, color = Color.White.copy(alpha = 0.1f)),
                shape = MaterialTheme.shapes.extraLarge
            )
            .clickable {
                onCardClick()

            },
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        shape = MaterialTheme.shapes.extraSmall,
    ) {

        Row(
            modifier = Modifier
                .fillMaxSize()
                .background(cardViews()),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            AnimatedVisibility(
                visible = !isBlurred, enter = scaleIn(),
                exit = fadeOut(targetAlpha = 0.5f) + scaleOut()
            ) {
                Text(text = cardText, color = Color.White, fontFamily = cabinFont.toFontFamily())
            }


        }
    }

    AnimatedVisibility(
        visible = isBlurred,
        enter = scaleIn(),
        exit = fadeOut(targetAlpha = 0.5f) + scaleOut()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            buttons.forEach { button ->
                button()

            }
        }
    }


}