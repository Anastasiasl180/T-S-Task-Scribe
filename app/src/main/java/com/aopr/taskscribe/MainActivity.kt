package com.aopr.taskscribe

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.aopr.shared_ui.navigation.LocalNavigator
import com.aopr.shared_ui.navigation.MainNavRoutes
import com.aopr.shared_ui.theme.TaskScribeTheme
import com.aopr.shared_ui.util.global_view_model.GlobalViewModel
import com.aopr.shared_ui.util.global_view_model.GlobalViewModelStoreOwner
import com.aopr.shared_ui.util.global_view_model.events.GlobalUiEvents
import com.aopr.tasks_data.scheduled_notifucation.scheduleDailyCheck
import com.aopr.taskscribe.ui.AppNavHost
import com.aopr.taskscribe.ui.BottomBar
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        scheduleDailyCheck(this)
        enableEdgeToEdge()
        setContent {
            val globalViewModel =
                koinViewModel<GlobalViewModel>(viewModelStoreOwner = GlobalViewModelStoreOwner)
            val isBottomBarShowed = globalViewModel.isBottomBarShowed.collectAsState()
            val navHost = rememberNavController()
            GlobalUiEventHandler(navHost = navHost)

            TaskScribeTheme(taskScribeThemesFlow = globalViewModel.chosenTheme) {

                Scaffold(bottomBar = {

                    if (isBottomBarShowed.value) {
                        BottomBar(navController = navHost)
                    }
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


    @Composable
    fun GlobalUiEventHandler(navHost: NavHostController) {
        val globalViewModel =
            koinViewModel<GlobalViewModel>(viewModelStoreOwner = GlobalViewModelStoreOwner)
        LaunchedEffect(Unit) {
            globalViewModel.event.collect() { event ->
                when (event) {
                    GlobalUiEvents.NavigateToAiScreen -> {
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

                    GlobalUiEvents.NavigateToDashBoardScreen -> {
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

                    GlobalUiEvents.NavigateToHomeScreen -> {
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

                    GlobalUiEvents.NavigateBack -> {
                        navHost.navigate(MainNavRoutes.HomeNavHost)
                    }
                }
            }
        }
    }
}

