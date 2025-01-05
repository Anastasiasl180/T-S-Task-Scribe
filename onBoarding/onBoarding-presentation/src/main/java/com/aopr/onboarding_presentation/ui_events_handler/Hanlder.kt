package com.aopr.onboarding_presentation.ui_events_handler

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import com.aopr.home.home_screen.navigation.HomeNavRoutes
import com.aopr.onboarding_presentation.events.first_screen_events.FirstScreenUiEvents
import com.aopr.onboarding_presentation.events.loading_screen_events.LoadingUiEvents
import com.aopr.onboarding_presentation.events.second_screen_events.SecondScreenUiEvents
import com.aopr.onboarding_presentation.model.FirstOnBoardingViewModel
import com.aopr.onboarding_presentation.model.LoadingViewModel
import com.aopr.onboarding_presentation.model.SecondOnBoardingViewModel
import com.aopr.onboarding_presentation.navigation.OnBoardingNavRoutes
import com.aopr.shared_ui.MainViewModel
import com.aopr.shared_ui.navigation.AuthenticationRoutes
import com.aopr.shared_ui.util.LocalNavigator
import com.aopr.shared_ui.util.MainViewModelStoreOwner
import com.aopr.shared_ui.util.currentOrThrow
import kotlinx.coroutines.delay
import org.koin.androidx.compose.koinViewModel

@Composable
fun LoadingUiEventsHandler() {
    val viewModel = koinViewModel<LoadingViewModel>()
    val mainViewModel = koinViewModel<MainViewModel>(viewModelStoreOwner = MainViewModelStoreOwner)
    val isFirstLaunched = mainViewModel.isFirstLaunch.collectAsState()
    val navigator = LocalNavigator.currentOrThrow()

    LaunchedEffect(key1 = Unit) {
        viewModel.event.collect { event ->
            when (event) {
                LoadingUiEvents.NavigateToFirstOnBoardingScreenOrHomeScreen -> {
                    if (isFirstLaunched.value) {
                        Log.wtf("Meerkalaunch",isFirstLaunched.value.toString())
                        navigator.navigate(OnBoardingNavRoutes.FirstScreen)
                    } else {
                        Log.wtf("Meerkalaunch",isFirstLaunched.value.toString())

                        navigator.navigate(HomeNavRoutes.HomeScreen)
                        mainViewModel.onEvent(MainViewModel.MainEvent.ShowBottomBar)
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
    val mainViewModel = koinViewModel<MainViewModel>(viewModelStoreOwner = MainViewModelStoreOwner)
    val navigator = LocalNavigator.currentOrThrow()
    LaunchedEffect(key1 = Unit) {

        viewModel.event.collect { events ->
            when (events) {
                SecondScreenUiEvents.NavigateToHome -> {
                    navigator.navigate(AuthenticationRoutes.RegistrationScreen)
                    mainViewModel.onEvent(MainViewModel.MainEvent.SetFirstLaunchTrue)
                    delay(500)
                    mainViewModel.onEvent(MainViewModel.MainEvent.ShowBottomBar)
                }

                SecondScreenUiEvents.NavigateBack -> {
                    navigator.popBackStack()
                }

                 }
        }
    }
}
