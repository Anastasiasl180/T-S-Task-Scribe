package com.aopr.shared_ui.cardsView

import androidx.compose.animation.core.VisibilityThreshold
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
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

        Themes.NATURE -> {

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

        Themes.SUNSET -> {
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

        Themes.BROWN -> {

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
        Themes.NATURE -> {
            Brush.linearGradient(
                colorStops = arrayOf(
                    0.0f to MaterialTheme.colorScheme.onPrimary,
                    0.4f to MaterialTheme.colorScheme.onBackground,
                    0.75f to MaterialTheme.colorScheme.primaryContainer,
                    0.99f to MaterialTheme.colorScheme.onSecondary,
                ), end = Offset.Infinite
            )
        }

        Themes.SUNSET -> {
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

        Themes.BROWN -> {
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
fun textGradient(): List<Color> {
    val mainViewModel = koinViewModel<MainViewModel>(viewModelStoreOwner = MainViewModelStoreOwner)
    val themes by mainViewModel.chosenTheme.collectAsState()

    val text: List<Color> = when (themes) {
        Themes.NATURE -> {
            listOf(
                MaterialTheme.colorScheme.primaryContainer,
                MaterialTheme.colorScheme.onBackground,
                MaterialTheme.colorScheme.primaryContainer,
            )
        }

        Themes.SUNSET -> {
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
fun colorsForThemeCards() {

}