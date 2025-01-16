package com.aopr.shared_ui.theme

import android.os.Build
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.aopr.shared_domain.colors_for_theme.Themes
import kotlinx.coroutines.flow.StateFlow

val blueTheme = darkColorScheme(
    primary =Color(0xFF000000),
    secondary =  Color(0xFF0D1A39),
    tertiary =  Color(0xFF122454),
    onPrimary =Color(0xFF152F6A),
    primaryContainer = Color(0xFF384662),
    onPrimaryContainer = Color(0xFFF5F5F5)
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
val metalicTheme = darkColorScheme(
    primary =  Color(0xFFFE3200),
    secondary =   Color(0xFFFD6E47),
    tertiary = Color(0xFFE4A596),
    onPrimary =  Color(0xFFCCCCCC),
    onBackground = Color(0xFF4D4D4D),
    primaryContainer = Color(0xFF343434),
    onSecondary =   Color(0xFF010101),


)
val pastelTheme = darkColorScheme(
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




val dusky_evening = darkColorScheme(
    tertiary =  Color(0xFFE76F7F),
    onBackground =Color(0xFF086F7A),
    onSecondary =   Color(0xFF071F23),
    primaryContainer = Color(0xFF070506),

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

    val taskScribeThemes = taskScribeThemesFlow.collectAsState()
    val colorScheme =
        when {
            dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
                if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
            }
            darkTheme -> when (taskScribeThemes.value) {
                Themes.BLUE -> blueTheme
                Themes.VIOLET -> violetTheme
                Themes.HAKI -> hakiTheme
                Themes.METALIC -> metalicTheme
                Themes.`DUSKY-EVENING` -> dusky_evening
                Themes.ORANGE -> orangeTheme
                Themes.PASTEL -> pastelTheme
            }
            else -> LightColorScheme
        }


    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}