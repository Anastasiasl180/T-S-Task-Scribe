package com.aopr.taskscribe.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.aopr.home.home_screen.navigation.DrawerNavRoutes
import com.aopr.home.home_screen.navigation.HomeNavRoutes
import com.aopr.home.home_screen.ui.HomeScreen
import com.aopr.home.home_screen.ui.drawers_screens.about_app_screen.AboutAppScreen
import com.aopr.home.home_screen.ui.drawers_screens.privacy_policy_screen.PrivacyPolicyScreen
import com.aopr.home.home_screen.ui.drawers_screens.profile_screen.ProfileScreen
import com.aopr.home.home_screen.ui.drawers_screens.settings_screen.SettingsScreen
import com.aopr.home.home_screen.ui.drawers_screens.theme_screen.ThemeChooserScreen
import com.aopr.notes_presentation.navigation.AllNotesRoutes
import com.aopr.notes_presentation.ui.AllNotesScreen
import com.aopr.notes_presentation.ui.CreatingNoteScreen
import com.aopr.shared_ui.navigation.LocalNavigator
import com.aopr.shared_ui.navigation.MainNavRoutes
import com.aopr.shared_ui.navigation.currentOrThrow
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
import com.example.bookmarks_presentation.view_models.ui_events_handlers.creating_bookmark_handler.CreatingBookmarkUiEventHandlerWithCategoryId
import com.example.calendar_presentation.ui.CalendarScreen
import kotlin.reflect.typeOf

@RequiresApi(Build.VERSION_CODES.P)
@Composable
fun AppNavHost() {

    val navigator = LocalNavigator.currentOrThrow()

    NavHost(navController = navigator, startDestination = HomeNavRoutes.HomeScreen) {
        composable<HomeNavRoutes.HomeScreen> {
            val innerNavigator = rememberNavController()
            CompositionLocalProvider(LocalNavigator provides innerNavigator) {
                NavHost(
                    navController = innerNavigator,
                    startDestination = HomeNavRoutes.HomeScreen
                ) {
                    /*   composable<AuthenticationRoutes.RegistrationScreen> {
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
                        }*/
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
                    composable<DrawerNavRoutes.ThemesScreen> {
                        ThemeChooserScreen()
                    }
                    composable<DrawerNavRoutes.SettingsScreen> {
                        SettingsScreen()
                    }
                    composable<DrawerNavRoutes.AboutAppScreen> {
                        AboutAppScreen()
                    }
                    composable<DrawerNavRoutes.ProfileScreen> {
                        ProfileScreen()
                    }
                    composable<DrawerNavRoutes.PrivacyPolicyScreen> {
                        PrivacyPolicyScreen()
                    }
                    composable<HomeNavRoutes.CalendarScreen> {
                        CalendarScreen()
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