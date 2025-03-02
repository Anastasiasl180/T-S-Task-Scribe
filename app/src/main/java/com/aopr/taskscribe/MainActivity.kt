package com.aopr.taskscribe

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.aopr.authentication_presentation.ui.LogInScreen
import com.aopr.shared_ui.navigation.LocalNavigator
import com.aopr.shared_ui.navigation.MainNavRoutes
import com.aopr.shared_ui.theme.TaskScribeTheme
import com.aopr.shared_ui.util.global_view_model.GlobalViewModel
import com.aopr.shared_ui.util.global_view_model.GlobalViewModelStoreOwner
import com.aopr.shared_ui.util.global_view_model.events.GlobalUiEvents
import com.aopr.tasks_data.scheduled_notifucation.scheduleDailyCheck
import com.aopr.taskscribe.ui.BottomBar
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {

    private val NOTIFICATION_PERMISSION_REQUEST_CODE = 1001

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        scheduleDailyCheck(this)
        checkAndRequestNotificationPermission()
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
                       // AppNavHost()
                        LogInScreen()
                    }
                }

            }
        }
    }

    private fun checkAndRequestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(android.Manifest.permission.POST_NOTIFICATIONS),
                    NOTIFICATION_PERMISSION_REQUEST_CODE
                )
            } else {
                Log.d("MainActivity", "Notification permission already granted.")
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == NOTIFICATION_PERMISSION_REQUEST_CODE) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                // Permission granted
                Log.d("MainActivity", "Notification permission granted.")
            } else {
                // Permission denied
                Log.d("MainActivity", "Notification permission denied.")
                // Optionally, inform the user that notifications won't appear
            }
        }
    }
}

@Composable
fun GlobalUiEventHandler(navHost: NavHostController) {
    val globalViewModel = koinViewModel<GlobalViewModel>(viewModelStoreOwner = GlobalViewModelStoreOwner)
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

