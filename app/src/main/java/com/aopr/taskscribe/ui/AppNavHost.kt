package com.aopr.taskscribe.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.aopr.authentication_presentation.navigation.AuthenticationRoutes
import com.aopr.authentication_presentation.ui.LogInScreen
import com.aopr.authentication_presentation.ui.RegistrationScreen
import com.aopr.home.home_screen.ui.HomeScreen
import com.aopr.home.home_screen.drawers_screens.theme_screen.ThemeChooserScreen
import com.aopr.home.home_screen.navigation.DrawerNavRoutes
import com.aopr.home.home_screen.navigation.HomeNavRoutes
import com.aopr.notes_presentation.navigation.AllNotesRoutes
import com.aopr.notes_presentation.ui.AllNotesScreen
import com.aopr.notes_presentation.ui.CreatingNoteScreen
import com.aopr.onboarding_presentation.navigation.OnBoardingNavRoutes
import com.aopr.onboarding_presentation.ui.FirstOnBoardingScreen
import com.aopr.onboarding_presentation.ui.LoadingScreen
import com.aopr.onboarding_presentation.ui.SecondOnBoardingScreen
import com.aopr.shared_ui.navigation.MainNavRoutes
import com.aopr.shared_ui.util.LocalNavigator
import com.aopr.shared_ui.util.currentOrThrow
import com.aopr.tasks_presentation.navigation.AllTasksNavRoutes
import com.aopr.tasks_presentation.ui.AllTasksScreen
import com.aopr.tasks_presentation.ui.CreatingTaskScreen
import com.example.bookmarks_presentation.navigation.BookmarksNavRoutes
import com.example.bookmarks_presentation.navigation.BookmarksRoutesInfo
import com.example.bookmarks_presentation.navigation.getBookmarksTypeMap
import com.example.bookmarks_presentation.ui.AllBookmarksInCategory
import com.example.bookmarks_presentation.ui.AllBookmarksScreen
import com.example.bookmarks_presentation.ui.CreatingBookMarkScreen
import com.example.bookmarks_presentation.ui.MainBookmarksScreen
import com.example.bookmarks_presentation.ui_events_handlers.creating_bookmark_handler.CreatingBookmarkUiEventHandlerWithCategoryId
import kotlin.reflect.typeOf

@Composable
fun AppNavHost() {

    val navigator = LocalNavigator.currentOrThrow()

    NavHost(navController = navigator, startDestination = MainNavRoutes.HomeNavHost) {
        composable<MainNavRoutes.HomeNavHost> {
            val innerNavigator = rememberNavController()
            CompositionLocalProvider(LocalNavigator provides innerNavigator) {
                NavHost(
                    navController = innerNavigator,
                    startDestination = AuthenticationRoutes.RegistrationScreen
                ) {
                    composable<AuthenticationRoutes.RegistrationScreen> {
                        RegistrationScreen()
                    }
                    composable<AuthenticationRoutes.LogInScreen> {
                       LogInScreen()
                    }
                    composable<OnBoardingNavRoutes.LoadingScreen> {
                        LoadingScreen()
                    }
                    composable<OnBoardingNavRoutes.FirstScreen> {
                        FirstOnBoardingScreen()
                    }
                    composable<OnBoardingNavRoutes.SecondScreen> {
                        SecondOnBoardingScreen()
                    }
                    composable<HomeNavRoutes.AllNotesScreen> {
                        AllNotesScreen()
                    }
                    composable<HomeNavRoutes.HomeScreen> {
                        HomeScreen()
                    }
                    composable<AllNotesRoutes.CreatingNoteScreen> {
                        CreatingNoteScreen()
                    }
                    composable<HomeNavRoutes.AllTasksScreen> {
                        AllTasksScreen()
                    }
                    composable<AllTasksNavRoutes.CreatingTaskScreen> {
                        CreatingTaskScreen()
                    }
                    composable<HomeNavRoutes.AllCategoriesOfBookmarks> {
                        MainBookmarksScreen()
                    }
                    composable<BookmarksNavRoutes.AllBookmarksInCategory> {
                        AllBookmarksInCategory()
                    }
                    composable<BookmarksNavRoutes.AllBookmarksScreen> {
                        AllBookmarksScreen()
                    }
                    composable<BookmarksNavRoutes.CreatingBookMarkScreen>(
                        typeMap = mapOf(typeOf<BookmarksRoutesInfo>() to getBookmarksTypeMap())
                    ) {
                        CreatingBookMarkScreen() {
                            CreatingBookmarkUiEventHandlerWithCategoryId()
                        }
                    }
                    composable<DrawerNavRoutes.SettingsScreen> {
                        ThemeChooserScreen()
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