package com.aopr.shared_ui.infoBar

import androidx.compose.animation.core.Ease
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.radusalagean.infobarcompose.InfoBar
import com.radusalagean.infobarcompose.InfoBarEasing
import com.radusalagean.infobarcompose.InfoBarMessage

@Composable
fun CustomInfoBar(message: InfoBarMessage) {
    Box(modifier = Modifier.fillMaxSize(0.9f), contentAlignment = Alignment.BottomCenter) {
        InfoBar(
            offeredMessage = message,
            modifier = Modifier
                .fillMaxWidth(0.7f)
                .height(50.dp), textAlign = TextAlign.Center,
            shape = RoundedCornerShape(20.dp),
            wrapInsideExpandedBox = false,
        ) {

        }
    }
}
