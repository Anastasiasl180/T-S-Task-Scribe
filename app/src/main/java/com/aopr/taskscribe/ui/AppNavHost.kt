package com.aopr.taskscribe.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.aopr.home.home_screen.HomeScreen
import com.aopr.notes_presentation.view_model.NotesViewModel
import com.aopr.shared_ui.MainViewModel
import com.aopr.shared_ui.navigation.HomeNavRoutes
import com.aopr.shared_ui.navigation.MainNavRoutes
import com.aopr.shared_ui.util.LocalNavigator
import com.aopr.shared_ui.util.currentOrThrow
import org.koin.androidx.compose.koinViewModel

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

                    }
                    composable<HomeNavRoutes.HomeScreen> {
                        val viewModel = koinViewModel<NotesViewModel>()
                        HomeScreen()
                        LaunchedEffect(key1 = Unit) {

                        }
                    }
                }
            }

        }
        composable<MainNavRoutes.AI> {
            val viewModel = koinViewModel<MainViewModel>()
        }
        composable<MainNavRoutes.DashBoard> {
            val viewModel = koinViewModel<MainViewModel>()
        }
    }
}