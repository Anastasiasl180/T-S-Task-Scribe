package com.aopr.home.home_screen.viewModel.events

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.aopr.home.home_screen.navigation.DrawerItems
import com.aopr.home.home_screen.navigation.DrawerNavRoutes
import com.aopr.home.home_screen.navigation.HomeNavRoutes
import com.aopr.home.home_screen.viewModel.events.homeEvents.HomeUiEvents
import com.aopr.shared_ui.util.LocalNavigator
import com.aopr.shared_ui.util.currentOrThrow
import org.koin.androidx.compose.koinViewModel


@Composable
fun HomeUiEventHandler() {
    val navigator = LocalNavigator.currentOrThrow()
    val viewModel = koinViewModel<HomeViewModel>()
    LaunchedEffect(Unit) {
        viewModel.uiEvents.collect { uiEvents ->
            when (uiEvents) {
                HomeUiEvents.NavigateToAllNotesScreen -> {
                    navigator.navigate(HomeNavRoutes.AllNotesScreen)

                }
                HomeUiEvents.NavigateToAllTasks -> {
                    navigator.navigate(HomeNavRoutes.AllTasksScreen)
                }

                HomeUiEvents.NavigateToAllCategoriesOfBookmarks -> {
                    navigator.navigate(HomeNavRoutes.AllCategoriesOfBookmarks)
                }

                HomeUiEvents.NavigateToThemesByDrawer -> {
                    navigator.navigate(DrawerNavRoutes.SettingsScreen)
                }
            }

        }
    }

}