package com.aopr.shared_ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.ui.graphics.vector.ImageVector
import com.aopr.shared_ui.MainViewModel
import kotlinx.serialization.Serializable

@Serializable
sealed interface MainNavRoutes {

    @Serializable
    data object HomeNavHost : MainNavRoutes

    @kotlinx.serialization.Serializable
    data object DashBoard : MainNavRoutes

    @Serializable
    data object AI : MainNavRoutes

}

enum class NavigationBarItems(
    val icon: ImageVector,
    val routes: MainNavRoutes,
    val event: MainViewModel.MainEvent
) {
    Home(
        icon = Icons.Default.Home,
        routes = MainNavRoutes.HomeNavHost,
        event = MainViewModel.MainEvent.NavigateToHomeScreen
    ),
    DashBoard(
        icon = Icons.Default.Menu,
        routes = MainNavRoutes.DashBoard,
        event = MainViewModel.MainEvent.NavigateToDashBoardScreen
    ),
    Ai(
        icon = Icons.Default.Face,
        routes = MainNavRoutes.AI,
        event = MainViewModel.MainEvent.NavigateToAiScreen
    )
}
