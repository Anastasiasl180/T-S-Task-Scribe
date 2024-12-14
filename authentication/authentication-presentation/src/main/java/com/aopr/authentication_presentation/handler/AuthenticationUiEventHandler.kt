package com.aopr.authentication_presentation.handler

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import com.aopr.authentication_presentation.di.AuthenticationPresentationModule
import com.aopr.authentication_presentation.events.RegistrationUiEvents
import com.aopr.authentication_presentation.model.RegistrationViewModel
import com.aopr.home.home_screen.navigation.HomeNavRoutes
import com.aopr.shared_ui.navigation.MainNavRoutes
import com.aopr.shared_ui.util.LocalNavigator
import com.aopr.shared_ui.util.currentOrThrow
import org.koin.androidx.compose.koinViewModel

@Composable
fun AuthenticationUiEventHandler() {
    val viewmodel = koinViewModel<RegistrationViewModel>()
    val navigator = LocalNavigator.currentOrThrow()
    LaunchedEffect(key1 = Unit) {
        viewmodel.event.collect { event ->
            when (event) {
                RegistrationUiEvents.NavigateToHomeScreen -> {
                    navigator.navigate(HomeNavRoutes.HomeScreen)
                }

                RegistrationUiEvents.NavigateToSignInScreen -> {

                }
            }

        }
    }
}