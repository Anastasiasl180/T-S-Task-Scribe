package com.aopr.taskscribe.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.aopr.home.home_screen.HomeScreen
import com.aopr.home.home_screen.navigation.HomeNavRoutes
import com.aopr.notes_presentation.navigation.AllNotesRoutes
import com.aopr.notes_presentation.ui.AllNotesScreen
import com.aopr.notes_presentation.ui.CreatingNoteScreen
import com.aopr.shared_ui.navigation.MainNavRoutes
import com.aopr.shared_ui.util.LocalNavigator
import com.aopr.shared_ui.util.currentOrThrow

@Composable
fun AppNavHost() {

    val navigator = LocalNavigator.currentOrThrow()

    NavHost(navController = navigator, startDestination = MainNavRoutes.HomeNavHost) {
        composable<MainNavRoutes.HomeNavHost> {
            val innerNavigator = rememberNavController()
            CompositionLocalProvider(LocalNavigator provides innerNavigator) {
                NavHost(
                    navController = innerNavigator,
                    startDestination = HomeNavRoutes.HomeScreen
                ) {
                    composable<HomeNavRoutes.AllNotesScreen> {
                        AllNotesScreen()
                    }
                    composable<HomeNavRoutes.HomeScreen> {
                        HomeScreen()
                    }
                    composable<AllNotesRoutes.CreatingNoteScreen> {
                        CreatingNoteScreen()
                    }
                }
            }

        }
        composable<MainNavRoutes.AI> {
        }
        composable<MainNavRoutes.DashBoard> {
        }
    }
}