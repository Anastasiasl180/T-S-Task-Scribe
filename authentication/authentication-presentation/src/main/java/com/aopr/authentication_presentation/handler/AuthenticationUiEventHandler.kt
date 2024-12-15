package com.aopr.authentication_presentation.handler

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.aopr.authentication_presentation.events.log_in_events.LogInUiEvents
import com.aopr.authentication_presentation.events.registration_events.RegistrationUiEvents
import com.aopr.authentication_presentation.model.LogInViewModel
import com.aopr.authentication_presentation.model.RegistrationViewModel
import com.aopr.authentication_presentation.navigation.AuthenticationRoutes
import com.aopr.home.home_screen.navigation.HomeNavRoutes
import com.aopr.shared_ui.util.LocalNavigator
import com.aopr.shared_ui.util.currentOrThrow
import org.koin.androidx.compose.koinViewModel

@Composable
fun RegistrationUiEventHandler() {
    val viewmodel = koinViewModel<RegistrationViewModel>()
    val navigator = LocalNavigator.currentOrThrow()
    LaunchedEffect(key1 = Unit) {
        viewmodel.event.collect { event ->
            when (event) {
                RegistrationUiEvents.NavigateToHomeScreen -> {
                    navigator.navigate(HomeNavRoutes.HomeScreen)
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
    LaunchedEffect(key1 = Unit) {
        viewmodel.event.collect { event ->
            when (event) {
                LogInUiEvents.NavigateToHomeScreen -> {
                    navigator.navigate(HomeNavRoutes.HomeScreen)
                }
            }

        }
    }
}