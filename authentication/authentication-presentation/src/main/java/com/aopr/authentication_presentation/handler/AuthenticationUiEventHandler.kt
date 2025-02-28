package com.aopr.authentication_presentation.handler

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.aopr.authentication_presentation.events.log_in_events.LogInUiEvents
import com.aopr.authentication_presentation.events.registration_events.RegistrationUiEvents
import com.aopr.authentication_presentation.model.LogInViewModel
import com.aopr.authentication_presentation.model.RegistrationViewModel
import com.aopr.home.home_screen.navigation.HomeNavRoutes
import com.aopr.shared_ui.util.global_view_model.GlobalViewModel
import com.aopr.shared_ui.navigation.AuthenticationRoutes
import com.aopr.shared_ui.navigation.LocalNavigator
import com.aopr.shared_ui.util.global_view_model.GlobalViewModelStoreOwner
import com.aopr.shared_ui.navigation.currentOrThrow
import com.aopr.shared_ui.util.global_view_model.events.GlobalEvents
import org.koin.androidx.compose.koinViewModel

@Composable
fun RegistrationUiEventHandler() {
    val viewmodel = koinViewModel<RegistrationViewModel>()
    val globalViewModel = koinViewModel<GlobalViewModel>(viewModelStoreOwner = GlobalViewModelStoreOwner)
    val navigator = LocalNavigator.currentOrThrow()
    LaunchedEffect(key1 = Unit) {
        viewmodel.event.collect { event ->
            when (event) {
                RegistrationUiEvents.NavigateToHomeScreen -> {
                    navigator.navigate(HomeNavRoutes.HomeScreen)
                    globalViewModel.onEvent(GlobalEvents.SetFirstLaunchToFalse)
                }

                RegistrationUiEvents.NavigateToLogInScreen -> {
                    navigator.navigate(AuthenticationRoutes.LogInScreen)
                }
            }

        }
    }
}

@Composable
fun LogInUiEventHandler() {
    val viewmodel = koinViewModel<LogInViewModel>()
    val navigator = LocalNavigator.currentOrThrow()
    val globalViewModel = koinViewModel<GlobalViewModel>(viewModelStoreOwner = GlobalViewModelStoreOwner)
    LaunchedEffect(key1 = Unit) {
        viewmodel.event.collect { event ->
            when (event) {
                LogInUiEvents.NavigateToHomeScreen -> {
                    navigator.navigate(HomeNavRoutes.HomeScreen)
                    globalViewModel.onEvent(GlobalEvents.SetFirstLaunchToFalse)
                }

            }

        }
    }
}