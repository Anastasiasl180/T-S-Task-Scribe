package com.example.calendar_presentation.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface CalendarNavRoutes {
    @Serializable
    data object CalendarScreen : CalendarNavRoutes
}