package com.aopr.shared_ui.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.navigation.NavHostController

val LocalNavigator = staticCompositionLocalOf<NavHostController?> { null }

@Composable
fun <T> ProvidableCompositionLocal<T?>.currentOrThrow(): T {
    return checkNotNull(this.current)
}
