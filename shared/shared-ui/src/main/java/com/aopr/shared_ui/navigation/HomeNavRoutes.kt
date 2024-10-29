package com.aopr.shared_ui.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface HomeNavRoutes {

    @Serializable
    data object AllNotesScreen:HomeNavRoutes

    @Serializable
    data object HomeScreen:HomeNavRoutes

}