package com.aopr.tasks_presentation.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface AllTasksNavRoutes {

    @Serializable
    data class CreatingTaskScreen(val id:Int?):AllTasksNavRoutes

}