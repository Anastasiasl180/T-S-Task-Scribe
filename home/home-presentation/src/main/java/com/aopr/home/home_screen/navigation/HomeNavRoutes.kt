package com.aopr.home.home_screen.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import kotlinx.serialization.Serializable

@Serializable
sealed interface HomeNavRoutes {

    @Serializable
    data object AllNotesScreen : HomeNavRoutes

    @Serializable
    data object HomeScreen : HomeNavRoutes

    @Serializable
    data object AllTasksScreen:HomeNavRoutes

    @Serializable
    data object AllCategoriesOfBookmarks:HomeNavRoutes

}

@Serializable
sealed interface DrawerNavRoutes {

    @Serializable
    data object SettingsScreen : DrawerNavRoutes

    @Serializable
    data object ProfileScreen : DrawerNavRoutes

    @Serializable
    data object AboutAppScreen : DrawerNavRoutes

}

enum class DrawerItems(val route: DrawerNavRoutes,text:String,icons:ImageVector) {
    SETTINGS(route = DrawerNavRoutes.SettingsScreen, text = "Theme", icons =Icons.Default.Settings),
    PROFILE(route = DrawerNavRoutes.ProfileScreen, text = "Profile", icons = Icons.Default.Face),
    ABOUT_APP(route = DrawerNavRoutes.AboutAppScreen, text = "About app", icons = Icons.Default.Info)
}