package com.aopr.taskscribe.ui

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.aopr.shared_ui.navigation.MainNavRoutes
import com.aopr.shared_ui.MainViewModel
import com.aopr.shared_ui.navigation.NavigationBarItems
import com.aopr.shared_ui.util.MainViewModelStoreOwner
import org.koin.androidx.compose.koinViewModel


@Composable
fun BottomBar(navController: NavHostController) {
    val mainViewModel = koinViewModel<MainViewModel>(viewModelStoreOwner = MainViewModelStoreOwner)
    val navItems = remember {
        NavigationBarItems.entries.toList()
    }

    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = currentBackStackEntry?.destination
    val currentRoute = currentDestination?.route

    NavigationBar {
        navItems.forEach { mainNavRoute ->
            val isSelected = mainNavRoute.routes::class.qualifiedName == currentRoute
            NavigationBarItem(selected = isSelected,
                onClick = {
                    when (mainNavRoute.routes) {
                        MainNavRoutes.AI -> {
                            mainViewModel.onEvent(MainViewModel.MainEvent.NavigateToAiScreen)
                        }

                        MainNavRoutes.DashBoard -> {
                            mainViewModel.onEvent(MainViewModel.MainEvent.NavigateToDashBoardScreen)
                        }

                        MainNavRoutes.HomeNavHost -> {
                            mainViewModel.onEvent(MainViewModel.MainEvent.NavigateToHomeScreen)
                        }
                    }

                }, icon = {
                    Icon(
                        imageVector = mainNavRoute.icon,
                        contentDescription = "",
                        tint = if (isSelected) Color.Cyan else Color.Yellow
                    )
                })

        }
    }

}