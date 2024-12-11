package com.aopr.shared_domain.colors_for_theme

import androidx.compose.ui.graphics.Color
import com.aopr.shared_domain.theme.Colors


enum class Themes(val themeColors: List<Color>){
    BLUE(Colors.blueTheme),
    VIOLET(Colors.violetTheme),
    HAKI(Colors.hakiTheme),
    PINK(Colors.pinkTheme),
    NATURE(Colors.natureTheme),
    SUNSET(Colors.sunsetTheme),
    ORANGE(Colors.orangeTheme),
    BROWN(Colors.brownTheme)
}