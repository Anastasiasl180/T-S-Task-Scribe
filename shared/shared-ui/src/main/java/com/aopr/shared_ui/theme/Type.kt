package com.aopr.shared_ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontLoadingStrategy
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.aopr.shared_ui.R

val cabinFont = Font(R.font.cabin, loadingStrategy = FontLoadingStrategy.Async)
val cabinFontFamily = FontFamily(cabinFont)

val interFont = Font(R.font.inter, loadingStrategy = FontLoadingStrategy.Async)
val interFontFamily = FontFamily(interFont)

val opens_sans = Font(R.font.open_sans_light, loadingStrategy = FontLoadingStrategy.Async)
val open_sansFontFamily = FontFamily(opens_sans)

val roboto = Font(R.font.roboto, loadingStrategy = FontLoadingStrategy.Async)
val robotoFontFamily = FontFamily(roboto)


// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = cabinFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    titleLarge = TextStyle(
        fontFamily = cabinFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 24.sp,
        lineHeight = 32.sp,
        letterSpacing = 0.5.sp
    ) ,
    headlineLarge = TextStyle(
        fontFamily = cabinFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 24.sp,
        lineHeight = 32.sp,
        letterSpacing = 0.5.sp
    ),

    /* Other default text styles to override
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    */
)