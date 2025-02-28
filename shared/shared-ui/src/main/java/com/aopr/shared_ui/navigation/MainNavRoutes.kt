package com.aopr.shared_ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.ui.graphics.vector.ImageVector
import com.aopr.shared_ui.util.global_view_model.events.GlobalEvents
import kotlinx.serialization.Serializable

@Serializable
sealed interface MainNavRoutes {

    @Serializable
    data object HomeNavHost : MainNavRoutes

    @Serializable
    data object DashBoard : MainNavRoutes

    @Serializable
    data object AI : MainNavRoutes

}

enum class NavigationBarItems(
    val icon: ImageVector,
    val routes: MainNavRoutes,
    val event: GlobalEvents
) {
    Home(
        icon = Icons.Default.Home,
        routes = MainNavRoutes.HomeNavHost,
        event = GlobalEvents.NavigateToHomeScreen
    ),
    DashBoard(
        icon = Icons.Default.Menu,
        routes = MainNavRoutes.DashBoard,
        event =GlobalEvents.NavigateToDashBoardScreen
    ),
    Ai(
        icon = Icons.Default.Face,
        routes = MainNavRoutes.AI,
        event = GlobalEvents.NavigateToAiScreen
    )
}
