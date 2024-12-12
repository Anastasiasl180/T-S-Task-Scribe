package com.aopr.shared_ui.theme

import android.os.Build
import android.util.Log
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.aopr.shared_domain.colors_for_theme.Themes
import kotlinx.coroutines.flow.StateFlow

val blueTheme = darkColorScheme(
    primary =Color(0xFF000000),
    secondary =  Color(0xFF0D1A39),
    tertiary =  Color(0xFF122454),
    onPrimary =Color(0xFF152F6A),
    primaryContainer = Color(0xFF384662)
)
val violetTheme = darkColorScheme(
    primary =  Color(0xFF000000),
    secondary = Color(0xFF461851),
    tertiary = Color(0xFF1E0C27),
    onPrimary =  Color(0xFF1D0E36),
    primaryContainer = Color(0xFF311872)
)
val hakiTheme = darkColorScheme(
    primary =  Color(0xFF000000),
    secondary =  Color(0xFF23222A),
    tertiary = Color(0xFF325450),
    onPrimary =  Color(0xFF345F55),
    primaryContainer =  Color(0xFF49A288)
)
val pinkTheme = darkColorScheme(
    primary = Color(0xFFEF3BCF),
    secondary =  Color(0xFFF64791),
    tertiary =  Color(0xFFF84D76),
    onPrimary = Color(0xFFF95160),
)
val natureTheme = darkColorScheme(
    primary =  Color(0xFF1E1E20),
    secondary =   Color(0xFF0F104F),
    tertiary = Color(0xFF17424C),
    onPrimary =  Color(0xFF859A79),
    onBackground = Color(0xFFF0EA8A),
    primaryContainer = Color(0xFFFEED31),
    onSecondary =  Color(0xFFFEA267),
    surfaceVariant =   Color(0xFFFE7D45),
    surface =  Color(0xFF79ABB7),
)
val brownTheme = darkColorScheme(
    primary =  Color(0xFFF58673),
    secondary =  Color(0xFFF58673),
    tertiary =  Color(0xFFC87163),
    onPrimary =   Color(0xFFB7685D),
    onBackground = Color(0xFF774745),
    primaryContainer = Color(0xFF4C2E34),
    onSecondary =   Color(0xFF271B25),
    secondaryContainer =   Color(0xFF000000)
)
val orangeTheme = darkColorScheme(
    primary =  Color(0xFF272727),
    secondary =  Color(0xFF613826),
    tertiary =  Color(0xFFB45225),
    onPrimary = Color(0xFFD25D25),
    onBackground =Color(0xFFE06E2A)
)
val sunsetTheme = darkColorScheme(
    primary =  Color(0xFFFD9C3B),
    secondary = Color(0xFFDE4044),
    tertiary =  Color(0xFF442B6B)
)
/*
private val DarkColorScheme =
    darkColorScheme(
        primary = Purple80,
        secondary = PurpleGrey80,
        tertiary = Pink80
    )*/

private val LightColorScheme = lightColorScheme(
    primary =  Color(0xFFFD9C3B),
    secondary = Color(0xFFDE4044),
    tertiary = Color(0xFF442B6B)

    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)

@Composable
fun TaskScribeTheme(
    taskScribeThemesFlow: StateFlow<Themes>,
    darkTheme: Boolean = true,
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val context = LocalContext.current

    // Колекція теми зі StateFlow
    val taskScribeThemes by taskScribeThemesFlow.collectAsState()
    val colorScheme = remember(taskScribeThemes) {
        when {
            dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
                if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
            }
            darkTheme -> when (taskScribeThemes) {
                Themes.BLUE -> blueTheme
                Themes.VIOLET -> violetTheme
                Themes.HAKI -> hakiTheme
                Themes.PINK -> pinkTheme
                Themes.NATURE -> natureTheme
                Themes.SUNSET -> sunsetTheme
                Themes.ORANGE -> orangeTheme
                Themes.BROWN -> brownTheme
            }
            else -> LightColorScheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}