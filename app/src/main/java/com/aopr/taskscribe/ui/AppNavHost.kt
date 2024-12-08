package com.aopr.taskscribe.ui

import android.util.Log
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
import com.aopr.tasks_presentation.navigation.AllTasksNavRoutes
import com.aopr.tasks_presentation.ui.AllTasksScreen
import com.aopr.tasks_presentation.ui.CreatingTaskScreen
import com.example.bookmarks_presentation.navigation.AllBookmarksByCategoryNavRoutes
import com.example.bookmarks_presentation.navigation.AllBookmarksNavRoutes
import com.example.bookmarks_presentation.navigation.AllCategoriesOfBookmarksNavRoutes
import com.example.bookmarks_presentation.ui.AllBookmarksInCategory
import com.example.bookmarks_presentation.ui.AllBookmarksScreen
import com.example.bookmarks_presentation.ui.CreatingBookMarkScreen
import com.example.bookmarks_presentation.ui.MainBookmarksScreen
import com.example.bookmarks_presentation.ui_events_handlers.creating_bookmark_handler.CreatingBookmarkUiEventHandlerWithBookmarkId
import com.example.bookmarks_presentation.ui_events_handlers.creating_bookmark_handler.CreatingBookmarkUiEventHandlerWithCategoryId

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
                    composable<HomeNavRoutes.AllTasksScreen> {
                        AllTasksScreen()
                    }
                    composable<AllTasksNavRoutes.CreatingTaskScreen> {
                        CreatingTaskScreen()
                    }
                    composable<HomeNavRoutes.AllCategoriesOfBookmarks> {
                        MainBookmarksScreen()
                    }
                    composable<AllCategoriesOfBookmarksNavRoutes.AllBookmarksInCategory> {
                        AllBookmarksInCategory()
                    }
                    composable<AllCategoriesOfBookmarksNavRoutes.AllBookmarksScreen> {
                        AllBookmarksScreen()
                    }
                    composable<AllCategoriesOfBookmarksNavRoutes.CreatingBookMarkScreen> {

                        CreatingBookMarkScreen(){
                            CreatingBookmarkUiEventHandlerWithBookmarkId()
                        }
                    }
                    composable<AllBookmarksByCategoryNavRoutes.CreatingBookmarkWithCategoryId>{

                        CreatingBookMarkScreen(){
                            CreatingBookmarkUiEventHandlerWithCategoryId()
                        }
                    }
                    composable<AllBookmarksNavRoutes.CreatingBookmarkScreen>{

                        CreatingBookMarkScreen(){
                            CreatingBookmarkUiEventHandlerWithBookmarkId()
                        }
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