package com.aopr.shared_ui.cardsView

import androidx.compose.animation.core.VisibilityThreshold
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.aopr.shared_domain.colors_for_theme.Themes
import com.aopr.shared_ui.MainViewModel
import com.aopr.shared_ui.util.MainViewModelStoreOwner
import org.koin.androidx.compose.koinViewModel


@Composable
fun cardViews(): Brush {
    val mainViewModel = koinViewModel<MainViewModel>(viewModelStoreOwner = MainViewModelStoreOwner)
    val themes by mainViewModel.chosenTheme.collectAsState()
    val colorsForCards: Brush = when (themes) {
        Themes.BLUE -> {
            Brush.radialGradient(
                colorStops = arrayOf(
                    0.0f to MaterialTheme.colorScheme.primary,
                    0.35f to MaterialTheme.colorScheme.secondary,
                    0.45f to MaterialTheme.colorScheme.tertiary,
                    0.75f to MaterialTheme.colorScheme.primaryContainer,
                    1.0f to MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.4f)
                ),
                center = Offset.VisibilityThreshold,
                radius = 1000f
            )
        }

        Themes.VIOLET -> {
            Brush.radialGradient(
                colors = listOf(
                    MaterialTheme.colorScheme.secondary,
                    MaterialTheme.colorScheme.onPrimary,
                    MaterialTheme.colorScheme.primaryContainer
                ),
                center = Offset.VisibilityThreshold,
                radius = 1000f
            )
        }

        Themes.HAKI -> {
            Brush.radialGradient(
                colors = listOf(
                    MaterialTheme.colorScheme.primary,
                    MaterialTheme.colorScheme.secondary,
                    MaterialTheme.colorScheme.tertiary.copy(alpha = 0.8f),
                    MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.4f),
                    MaterialTheme.colorScheme.onPrimaryContainer
                ),
                center = Offset.VisibilityThreshold,
                radius = 1000f
            )
        }

        Themes.METALIC -> {

            Brush.radialGradient(

                colorStops = arrayOf(
                    0.0f to MaterialTheme.colorScheme.primary,
                    0.3f to MaterialTheme.colorScheme.secondary,
                    0.5f to MaterialTheme.colorScheme.tertiary,
                    0.9f to MaterialTheme.colorScheme.onPrimary,
                ),
                center = Offset.VisibilityThreshold,
                radius = 1000f
            )
        }

        Themes.`DUSKY-EVENING` -> {
            Brush.radialGradient(
                colorStops = arrayOf(
                    0.2f to MaterialTheme.colorScheme.tertiary.copy(alpha = 0.7f),
                    0.9f to MaterialTheme.colorScheme.onSecondary,
                ),
                center = Offset.VisibilityThreshold,
                radius = 1000f
            )
        }

        Themes.ORANGE -> {
            Brush.radialGradient(
                colorStops = arrayOf(
                    0.0f to MaterialTheme.colorScheme.primary,
                    0.2f to MaterialTheme.colorScheme.secondary,
                    0.5f to MaterialTheme.colorScheme.tertiary,
                    0.8f to MaterialTheme.colorScheme.onBackground
                ),
                center = Offset.VisibilityThreshold,
                radius = 1000f
            )

        }

        Themes.PASTEL -> {

            Brush.radialGradient(
                colorStops = arrayOf(
                    0.0f to MaterialTheme.colorScheme.primary,
                    0.2f to MaterialTheme.colorScheme.secondary,
                    0.4f to MaterialTheme.colorScheme.tertiary,
                    0.45f to MaterialTheme.colorScheme.onPrimary,
                    0.6f to MaterialTheme.colorScheme.onBackground,
                    0.7f to MaterialTheme.colorScheme.primaryContainer,
                    0.8f to MaterialTheme.colorScheme.onSecondary,
                    0.9f to MaterialTheme.colorScheme.secondaryContainer
                ),
                center = Offset.VisibilityThreshold,
                radius = 1000f
            )
        }
    }
    return colorsForCards


}

@Composable
fun bookmarkCardsViews(): Brush {
    val mainViewModel = koinViewModel<MainViewModel>(viewModelStoreOwner = MainViewModelStoreOwner)
    val themes by mainViewModel.chosenTheme.collectAsState()
    val colorsForCards: Brush = when (themes) {
        Themes.BLUE -> {
            Brush.radialGradient(
                colorStops = arrayOf(
                    0.0f to MaterialTheme.colorScheme.primary,
                    0.35f to MaterialTheme.colorScheme.secondary,
                    0.45f to MaterialTheme.colorScheme.tertiary,
                    0.75f to MaterialTheme.colorScheme.primaryContainer,
                    1.0f to MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.4f)
                ),
                center = Offset.VisibilityThreshold,
                radius = 1000f
            )
        }

        Themes.VIOLET -> {
            Brush.radialGradient(
                colors = listOf(
                    MaterialTheme.colorScheme.secondary,
                    MaterialTheme.colorScheme.onPrimary,
                    MaterialTheme.colorScheme.primaryContainer
                ),
                center = Offset.VisibilityThreshold,
                radius = 1000f
            )
        }

        Themes.HAKI -> {
            Brush.radialGradient(
                colors = listOf(
                    MaterialTheme.colorScheme.primary,
                    MaterialTheme.colorScheme.secondary,
                    MaterialTheme.colorScheme.tertiary.copy(alpha = 0.8f),
                    MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.4f),
                    MaterialTheme.colorScheme.onPrimaryContainer
                ),
                center = Offset.VisibilityThreshold,
                radius = 1000f
            )
        }

        Themes.METALIC -> {

            Brush.radialGradient(

                colorStops = arrayOf(
                    0.0f to MaterialTheme.colorScheme.primary,
                    0.3f to MaterialTheme.colorScheme.secondary,
                    0.5f to MaterialTheme.colorScheme.tertiary,
                    0.9f to MaterialTheme.colorScheme.onPrimary,
                ),
                center = Offset.VisibilityThreshold,
                radius = 1000f
            )
        }

        Themes.`DUSKY-EVENING` -> {
            Brush.radialGradient(
                colorStops = arrayOf(
                    0.2f to MaterialTheme.colorScheme.tertiary.copy(alpha = 0.7f),
                    0.9f to MaterialTheme.colorScheme.onSecondary,
                ),
                center = Offset.VisibilityThreshold,
                radius = 1000f
            )
        }

        Themes.ORANGE -> {
            Brush.radialGradient(
                colorStops = arrayOf(
                    0.0f to MaterialTheme.colorScheme.primary,
                    0.2f to MaterialTheme.colorScheme.secondary,
                    0.5f to MaterialTheme.colorScheme.tertiary,
                    0.8f to MaterialTheme.colorScheme.onBackground
                ),
                center = Offset.VisibilityThreshold,
                radius = 1000f
            )

        }

        Themes.PASTEL -> {

            Brush.radialGradient(
                colorStops = arrayOf(
                    0.0f to MaterialTheme.colorScheme.primary,
                    0.2f to MaterialTheme.colorScheme.secondary,
                    0.4f to MaterialTheme.colorScheme.tertiary,
                    0.45f to MaterialTheme.colorScheme.onPrimary,
                    0.6f to MaterialTheme.colorScheme.onBackground,
                    0.7f to MaterialTheme.colorScheme.primaryContainer,
                    0.8f to MaterialTheme.colorScheme.onSecondary,
                    0.9f to MaterialTheme.colorScheme.secondaryContainer
                ),
                center = Offset.VisibilityThreshold,
                radius = 1000f
            )
        }
    }
    return colorsForCards


}


@Composable
fun background(): Brush {
    val mainViewModel = koinViewModel<MainViewModel>(viewModelStoreOwner = MainViewModelStoreOwner)

    val themes by mainViewModel.chosenTheme.collectAsState()


    val backgroundLooks: Brush = when (themes) {
        Themes.METALIC -> {
            Brush.linearGradient(
                colorStops = arrayOf(
                    0.0f to MaterialTheme.colorScheme.onPrimary,
                    0.4f to MaterialTheme.colorScheme.onBackground,
                    0.75f to MaterialTheme.colorScheme.primaryContainer,
                    0.99f to MaterialTheme.colorScheme.onSecondary,
                ), end = Offset.Infinite
            )
        }

        Themes.`DUSKY-EVENING` -> {
            Brush.linearGradient(
                colorStops = arrayOf(
                    0.0f to MaterialTheme.colorScheme.primaryContainer,
                    0.9f to MaterialTheme.colorScheme.onBackground,

                    ), start = Offset.Zero,
                end = Offset.Infinite
            )
        }

        Themes.ORANGE -> {
            Brush.linearGradient(
                colorStops = arrayOf(
                    0.0f to MaterialTheme.colorScheme.onSecondary,
                    0.5f to MaterialTheme.colorScheme.onSecondaryContainer,
                    0.99f to MaterialTheme.colorScheme.tertiary,

                    ), start = Offset.Zero,
                end = Offset.Infinite
            )
        }

        Themes.PASTEL -> {
            Brush.linearGradient(
                colorStops = arrayOf(
                    0.0f to MaterialTheme.colorScheme.onSecondary,
                    0.5f to MaterialTheme.colorScheme.onSecondaryContainer,
                    0.99f to MaterialTheme.colorScheme.primary,

                    ), start = Offset.Zero,
                end = Offset.Infinite
            )
        }

        else ->
            Brush.linearGradient(
                colorStops = arrayOf(
                    0.0f to MaterialTheme.colorScheme.primary,
                    0.4f to MaterialTheme.colorScheme.tertiary,
                    1.0f to MaterialTheme.colorScheme.onPrimary,
                    0.0f to MaterialTheme.colorScheme.onSecondary,
                    0.5f to MaterialTheme.colorScheme.onSecondaryContainer,
                    0.8f to MaterialTheme.colorScheme.primary,
                    1.0f to MaterialTheme.colorScheme.tertiary,

                    ), start = Offset.Zero,
                end = Offset.Infinite
            )
    }
    return backgroundLooks
}


@Composable
fun gradientOfDialogs(): Brush {
    val mainViewModel = koinViewModel<MainViewModel>(viewModelStoreOwner = MainViewModelStoreOwner)

    val themes by mainViewModel.chosenTheme.collectAsState()


    val backgroundLooks: Brush = when (themes) {
        Themes.METALIC -> {
            Brush.linearGradient(
                colorStops = arrayOf(
                    0.75f to colorResource(com.aopr.shared_ui.R.color.backgroundOfDialogs),
                    0.95f to MaterialTheme.colorScheme.secondary,
                    1f to colorResource(com.aopr.shared_ui.R.color.backgroundOfDialogs),
                ), start = Offset.Zero,
                end = Offset.Infinite
            )
        }

        Themes.`DUSKY-EVENING` -> {
            Brush.linearGradient(
                colorStops = arrayOf(
                    0.75f to colorResource(com.aopr.shared_ui.R.color.backgroundOfDialogs),
                    0.95f to MaterialTheme.colorScheme.onBackground,
                    1f to colorResource(com.aopr.shared_ui.R.color.backgroundOfDialogs),
                ), start = Offset.Zero,
                end = Offset.Infinite
            )
        }

        Themes.ORANGE -> {
            Brush.linearGradient(
                colorStops = arrayOf(
                    0.75f to colorResource(com.aopr.shared_ui.R.color.backgroundOfDialogs),
                    0.95f to MaterialTheme.colorScheme.onBackground,
                    1f to colorResource(com.aopr.shared_ui.R.color.backgroundOfDialogs),
                ), start = Offset.Zero,
                end = Offset.Infinite
            )
        }

        Themes.PASTEL -> {
            Brush.linearGradient(
                colorStops = arrayOf(
                    0.75f to colorResource(com.aopr.shared_ui.R.color.backgroundOfDialogs),
                    0.95f to MaterialTheme.colorScheme.secondary,
                    1f to colorResource(com.aopr.shared_ui.R.color.backgroundOfDialogs),
                ), start = Offset.Zero,
                end = Offset.Infinite
            )
        }

        Themes.VIOLET -> {
            Brush.linearGradient(
                colorStops = arrayOf(
                    0.75f to colorResource(com.aopr.shared_ui.R.color.backgroundOfDialogs),
                    0.95f to MaterialTheme.colorScheme.secondary,
                    1f to colorResource(com.aopr.shared_ui.R.color.backgroundOfDialogs),
                ), start = Offset.Zero,
                end = Offset.Infinite
            )
        }

        Themes.HAKI -> {
            Brush.linearGradient(
                colorStops = arrayOf(
                    0.75f to colorResource(com.aopr.shared_ui.R.color.backgroundOfDialogs),
                    0.95f to MaterialTheme.colorScheme.primaryContainer,
                    1f to colorResource(com.aopr.shared_ui.R.color.backgroundOfDialogs),
                ), start = Offset.Zero,
                end = Offset.Infinite
            )
        }

        Themes.BLUE -> {
            Brush.linearGradient(
                colorStops = arrayOf(
                    0.75f to colorResource(com.aopr.shared_ui.R.color.backgroundOfDialogs),
                    0.95f to MaterialTheme.colorScheme.onPrimary,
                    1f to colorResource(com.aopr.shared_ui.R.color.backgroundOfDialogs),
                ), start = Offset.Zero,
                end = Offset.Infinite
            )
        }

    }
    return backgroundLooks
}


@Composable
fun CircularCheckbox(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    circleSize: Dp = 20.dp,
    borderColor: Color = Color.Gray,
    checkedColor: Color = Color.White,
    uncheckedColor: Color = Color.Transparent
) {
    Box(
        modifier = modifier
            .size(circleSize)
            .clip(CircleShape)
            .background(if (checked) checkedColor else uncheckedColor)
            .border(2.dp, borderColor, CircleShape)
            .clickable {
                onCheckedChange(!checked)
            },
        contentAlignment = Alignment.Center
    ) {
        if (checked) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = null,
                tint = borderColor,
                modifier = Modifier.size(circleSize * 0.5f)
            )
        }
    }
}

@Composable
fun textGradient(): List<Color> {
    val mainViewModel = koinViewModel<MainViewModel>(viewModelStoreOwner = MainViewModelStoreOwner)
    val themes by mainViewModel.chosenTheme.collectAsState()

    val text: List<Color> = when (themes) {
        Themes.METALIC -> {
            listOf(
                MaterialTheme.colorScheme.primaryContainer,
                MaterialTheme.colorScheme.onBackground,
                MaterialTheme.colorScheme.primaryContainer,
            )
        }

        Themes.`DUSKY-EVENING` -> {
            listOf(
                MaterialTheme.colorScheme.tertiary.copy(alpha = 0.8f),
                MaterialTheme.colorScheme.tertiary.copy(alpha = 0.3f),
                MaterialTheme.colorScheme.tertiary.copy(alpha = 0.6f),
                MaterialTheme.colorScheme.tertiary.copy(alpha = 0.3f),
                MaterialTheme.colorScheme.tertiary.copy(alpha = 0.6f),
            )
        }

        Themes.ORANGE -> {
            listOf(
                MaterialTheme.colorScheme.tertiary,
                MaterialTheme.colorScheme.tertiary.copy(alpha = 0.6f),
                Color.White,
                MaterialTheme.colorScheme.tertiary.copy(alpha = 0.6f),
                Color.White,

                )
        }

        else ->
            listOf(
                MaterialTheme.colorScheme.secondary,
                MaterialTheme.colorScheme.secondary,
                Color.White,
                MaterialTheme.colorScheme.secondary, Color.White
            )
    }
    return text
}

@Composable
fun colorsForThemeCards(): List<Brush> {
    val listOfBlueTheme = Brush.radialGradient(
        colorStops = arrayOf(
            0.0f to Color(0xFF000000),
            0.3f to Color(0xFF0D1A39),
            0.45f to Color(0xFF122454),
            0.6f to Color(0xFF384662),
            1.0f to Color(0xFFF5F5F5)
        ),
        center = Offset.VisibilityThreshold,
        radius = 800f
    )

    val listOfVioletTheme = Brush.radialGradient(
        colors = listOf(
            Color(0xFF461851),
            Color(0xFF1D0E36),
            Color(0xFF311872)
        ),
        center = Offset.VisibilityThreshold,
        radius = 1000f
    )

    val lostOfHakiTheme = Brush.radialGradient(
        colors = listOf(
            Color(0xFF000000),
            Color(0xFF23222A),
            Color(0xFF325450),
            Color(0xFF49A288)
        ),
        center = Offset.VisibilityThreshold,
        radius = 900f
    )
    val listOfMetalicTheme = Brush.radialGradient(

        colorStops = arrayOf(
            0.0f to Color(0xFFFE3200),
            0.3f to Color(0xFFFD6E47),
            0.5f to Color(0xFFE4A596),
            0.9f to Color(0xFFCCCCCC),
        ),
        center = Offset.VisibilityThreshold,
        radius = 1000f


    )
    val listOfDuskyEveningTheme = Brush.radialGradient(
        colorStops = arrayOf(
            0.2f to Color(0xFFE76F7F),
            0.9f to Color(0xFF071F23),
        ),
        center = Offset.VisibilityThreshold,
        radius = 1000f
    )
    val listOfOrangeTheme = Brush.radialGradient(
        colorStops = arrayOf(
            0.0f to Color(0xFF272727),
            0.2f to Color(0xFF613826),
            0.5f to Color(0xFFB45225),
            0.8f to Color(0xFFE06E2A)
        ), center = Offset.VisibilityThreshold,
        radius = 1000f
    )
    val listOfPastelTheme = Brush.radialGradient(
        colorStops = arrayOf(

            0.0f to Color(0xFFF58673),
            0.2f to Color(0xFFF58673),
            0.4f to Color(0xFFC87163),
            0.45f to Color(0xFFB7685D),
            0.6f to Color(0xFF774745),
            0.7f to Color(0xFF4C2E34),
            0.8f to Color(0xFF271B25),
            0.9f to Color(0xFF000000)
        ),
        center = Offset.VisibilityThreshold,
        radius = 1000f
    )
    return listOf(
        listOfBlueTheme,
        listOfVioletTheme,
        lostOfHakiTheme,
        listOfMetalicTheme,
        listOfDuskyEveningTheme,
        listOfOrangeTheme,
        listOfPastelTheme
    )
}