package com.aopr.home.home_screen.view_model.ui_events_handler

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.aopr.home.home_screen.navigation.DrawerNavRoutes
import com.aopr.home.home_screen.navigation.HomeNavRoutes
import com.aopr.home.home_screen.view_model.HomeViewModel
import com.aopr.home.home_screen.view_model.events.homeEvents.HomeUiEvents
import com.aopr.notes_presentation.navigation.AllNotesRoutes
import com.aopr.shared_ui.navigation.AuthenticationRoutes
import com.aopr.shared_ui.navigation.LocalNavigator
import com.aopr.shared_ui.navigation.currentOrThrow
import com.aopr.tasks_presentation.navigation.AllTasksNavRoutes
import com.example.bookmarks_presentation.navigation.BookmarksNavRoutes
import com.example.bookmarks_presentation.navigation.BookmarksRoutesInfo
import com.example.calendar_presentation.navigation.CalendarNavRoutes
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
                    navigator.navigate(DrawerNavRoutes.ThemesScreen)
                }

                HomeUiEvents.NavigateToRegistrationScreen -> {
                    navigator.navigate(AuthenticationRoutes.RegistrationScreen)
                }

                HomeUiEvents.NavigateToCreateBookmarkScreen -> {
                    navigator.navigate(BookmarksNavRoutes.CreatingBookMarkScreen(BookmarksRoutesInfo(bookmarkId = null, categoryId = null)))
                }
                HomeUiEvents.NavigateToCreateNotesScreen -> {
                    navigator.navigate(AllNotesRoutes.CreatingNoteScreen(null))

                }
                HomeUiEvents.NavigateToCreateTaskScreen -> {
                    navigator.navigate(AllTasksNavRoutes.CreatingTaskScreen(id = null))

                }

                HomeUiEvents.NavigateToCalendarScreen -> {
                    navigator.navigate(HomeNavRoutes.CalendarScreen)
                }

                HomeUiEvents.NavigateToAboutAppByDrawer -> {
                    navigator.navigate(DrawerNavRoutes.AboutAppScreen)

                }
                HomeUiEvents.NavigateToPrivacyPolicyByDrawer -> {
                    navigator.navigate(DrawerNavRoutes.PrivacyPolicyScreen)

                }
                HomeUiEvents.NavigateToProfileByDrawer -> {
                    navigator.navigate(DrawerNavRoutes.ProfileScreen)

                }
                HomeUiEvents.NavigateToSettingsByDrawer -> {
                    navigator.navigate(DrawerNavRoutes.SettingsScreen)

                }
            }

        }
    }

}