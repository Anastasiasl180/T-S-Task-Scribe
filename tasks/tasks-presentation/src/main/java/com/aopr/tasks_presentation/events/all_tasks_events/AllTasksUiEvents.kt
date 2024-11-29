package com.aopr.tasks_presentation.events.all_tasks_events

import com.aopr.tasks_domain.models.Task

sealed class AllTasksUiEvents {
    data class NavigateToCreateTaskScreen(val id:Int?):AllTasksUiEvents()
    data class DeleteTask(val task:Task):AllTasksUiEvents()
}