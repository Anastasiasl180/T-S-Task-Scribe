package com.aopr.taskscribe

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.aopr.shared_ui.navigation.MainNavRoutes
import com.aopr.shared_ui.MainViewModel
import com.aopr.shared_ui.theme.TaskScribeTheme
import com.aopr.shared_ui.util.LocalNavigator
import com.aopr.shared_ui.util.MainViewModelStoreOwner
import com.aopr.taskscribe.ui.AppNavHost
import com.aopr.taskscribe.ui.BottomBar
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navHost = rememberNavController()
            GlobalUiEventHandler(navHost = navHost)
            TaskScribeTheme {
                Scaffold(bottomBar = {
                    BottomBar(navController = navHost)
                }) { _ ->
                    CompositionLocalProvider(
                        LocalNavigator provides navHost
                    ) {
                        AppNavHost()
                    }
                }
            }

        }
    }


}

@Composable
fun GlobalUiEventHandler(navHost: NavHostController) {
    val mainViewModel = koinViewModel<MainViewModel>(viewModelStoreOwner = MainViewModelStoreOwner)
    LaunchedEffect(Unit) {
        mainViewModel.event.collect() { event ->
            when (event) {
                MainViewModel.UiEvent.NavigateToAiScreen -> {
                    navHost.navigate(MainNavRoutes.AI) {
                        val currentRoute = navHost.currentDestination?.route
                        if (currentRoute != null) {
                            popUpTo(currentRoute) {
                                inclusive = true
                                saveState = true
                            }
                            restoreState = true
                        }
                    }

                }

                MainViewModel.UiEvent.NavigateToDashBoardScreen -> {
                    navHost.navigate(MainNavRoutes.DashBoard) {
                        val currentRoute = navHost.currentDestination?.route
                        if (currentRoute != null) {
                            popUpTo(currentRoute) {
                                inclusive = true
                                saveState = true
                            }
                            restoreState = true
                        }
                    }
                }

                MainViewModel.UiEvent.NavigateToHomeScreen -> {
                    navHost.navigate(MainNavRoutes.HomeNavHost) {
                        val currentRoute = navHost.currentDestination?.route
                        if (currentRoute != null) {
                            popUpTo(currentRoute) {
                                inclusive = true
                                saveState = true
                            }
                            restoreState = true
                        }
                    }
                }
            }
        }
    }
}
