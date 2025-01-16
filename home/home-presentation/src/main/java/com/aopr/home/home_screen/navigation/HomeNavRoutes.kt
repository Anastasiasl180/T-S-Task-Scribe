package com.aopr.home.home_screen.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.FavoriteBorder
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
    data object ThemesScreen : DrawerNavRoutes

    @Serializable
    data object ProfileScreen : DrawerNavRoutes

    @Serializable
    data object AboutAppScreen : DrawerNavRoutes

    @Serializable
    data object PrivacyPolicyScreen : DrawerNavRoutes

}

enum class DrawerItems(val route: DrawerNavRoutes,text:String,icons:ImageVector) {
    SETTINGS(route = DrawerNavRoutes.SettingsScreen, text = "Settings", icons =Icons.Default.Settings),
    THEMES(route = DrawerNavRoutes.ThemesScreen, text = "Theme", icons =Icons.Default.FavoriteBorder),
    PROFILE(route = DrawerNavRoutes.ProfileScreen, text = "Profile", icons = Icons.Default.Face),
    ABOUT_APP(route = DrawerNavRoutes.AboutAppScreen, text = "About app", icons = Icons.Default.Info),
    PRIVACY_POLICY(route = DrawerNavRoutes.SettingsScreen, text = "Settings", icons =Icons.Default.Settings),
}