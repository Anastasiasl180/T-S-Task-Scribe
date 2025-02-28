package com.aopr.tasks_presentation.view_models.events.all_tasks_events

import com.aopr.shared_ui.util.events_type.UiEventsType
import com.aopr.tasks_domain.models.Task

sealed class AllTasksUiEvents:UiEventsType {
    data class NavigateToCreateTaskScreen(val id:Int?): AllTasksUiEvents()
    data object NavigateBack: AllTasksUiEvents()
}