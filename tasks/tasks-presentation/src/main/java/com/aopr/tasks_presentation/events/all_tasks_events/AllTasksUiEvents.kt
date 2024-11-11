package com.aopr.tasks_presentation.events.all_tasks_events

sealed class AllTasksUiEvents {
    data class NavigateToCreateTaskScreen(val id:Int?):AllTasksUiEvents()
}