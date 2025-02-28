package com.aopr.onboarding_presentation.ui_events_handler

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import com.aopr.home.home_screen.navigation.HomeNavRoutes
import com.aopr.onboarding_presentation.events.first_screen_events.FirstScreenUiEvents
import com.aopr.onboarding_presentation.events.loading_screen_events.LoadingUiEvents
import com.aopr.onboarding_presentation.events.second_screen_events.SecondScreenUiEvents
import com.aopr.onboarding_presentation.model.FirstOnBoardingViewModel
import com.aopr.onboarding_presentation.model.LoadingViewModel
import com.aopr.onboarding_presentation.model.SecondOnBoardingViewModel
import com.aopr.onboarding_presentation.navigation.OnBoardingNavRoutes
import com.aopr.shared_ui.util.global_view_model.GlobalViewModel
import com.aopr.shared_ui.navigation.AuthenticationRoutes
import com.aopr.shared_ui.navigation.LocalNavigator
import com.aopr.shared_ui.util.global_view_model.GlobalViewModelStoreOwner
import com.aopr.shared_ui.navigation.currentOrThrow
import com.aopr.shared_ui.util.global_view_model.events.GlobalEvents
import org.koin.androidx.compose.koinViewModel

@Composable
fun LoadingUiEventsHandler() {
    val viewModel = koinViewModel<LoadingViewModel>()
    val globalViewModel = koinViewModel<GlobalViewModel>(viewModelStoreOwner = GlobalViewModelStoreOwner)
    val isFirstLaunched = globalViewModel.isFirstLaunch.collectAsState()
    val navigator = LocalNavigator.currentOrThrow()

    LaunchedEffect(key1 = Unit) {
        viewModel.event.collect { event ->
            when (event) {
                LoadingUiEvents.NavigateToFirstOnBoardingScreenOrHomeScreen -> {
                    if (isFirstLaunched.value) {
                        navigator.navigate(OnBoardingNavRoutes.FirstScreen)
                    } else {
                        navigator.navigate(HomeNavRoutes.HomeScreen)
                        globalViewModel.onEvent(GlobalEvents.ShowBottomBar)
                    }
                }
            }

        }
    }

}

@Composable
fun FirstScreenOnBoardingUiEventsHandler() {
    val viewModel = koinViewModel<FirstOnBoardingViewModel>()
    val navigator = LocalNavigator.currentOrThrow()
    LaunchedEffect(key1 = Unit) {
        viewModel.event.collect { events ->
            when (events) {
                FirstScreenUiEvents.NavigateToSecondScreen -> {
                    navigator.navigate(OnBoardingNavRoutes.SecondScreen)
                }
            }
        }
    }
}

@Composable
fun SecondScreenOnBoardingUiEventsHandler() {
    val viewModel = koinViewModel<SecondOnBoardingViewModel>()
    val globalViewModel = koinViewModel<GlobalViewModel>(viewModelStoreOwner = GlobalViewModelStoreOwner)
    val navigator = LocalNavigator.currentOrThrow()
    LaunchedEffect(key1 = Unit) {
        viewModel.event.collect { events ->
            when (events) {
                SecondScreenUiEvents.NavigateToHome -> {
                    navigator.navigate(AuthenticationRoutes.RegistrationScreen)

                }

                SecondScreenUiEvents.NavigateBack -> {
                    navigator.popBackStack()
                }

            }
        }
    }
}
