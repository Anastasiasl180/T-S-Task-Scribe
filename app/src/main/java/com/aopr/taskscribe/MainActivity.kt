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
import androidx.compose.runtime.getValue
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.aopr.shared_ui.MainViewModel
import com.aopr.shared_ui.navigation.MainNavRoutes
import com.aopr.shared_ui.theme.TaskScribeTheme
import com.aopr.shared_ui.util.LocalNavigator
import com.aopr.shared_ui.util.MainViewModelStoreOwner
import com.aopr.taskscribe.ui.AppNavHost
import com.aopr.taskscribe.ui.BottomBar
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {

    private val NOTIFICATION_PERMISSION_REQUEST_CODE = 1001

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkAndRequestNotificationPermission()
        enableEdgeToEdge()
        setContent {
            val mainViewModel = koinViewModel<MainViewModel>(viewModelStoreOwner = MainViewModelStoreOwner)
            val isBottomBarShowed = mainViewModel.isBottomBarShowed.collectAsState()
            val navHost = rememberNavController()
            GlobalUiEventHandler(navHost = navHost)

            TaskScribeTheme(taskScribeThemesFlow = mainViewModel.chosenTheme) {

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

