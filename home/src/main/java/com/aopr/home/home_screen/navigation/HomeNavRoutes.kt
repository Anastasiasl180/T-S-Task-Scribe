package com.aopr.home.home_screen.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface HomeNavRoutes {

    @Serializable
    data object AllNotesScreen: HomeNavRoutes

    @Serializable
    data object HomeScreen: HomeNavRoutes

}