package com.aopr.tasks_presentation.events.all_tasks_events

import android.icu.text.IDNA.Info
import com.aopr.shared_ui.util.events_type.EventsType
import com.aopr.tasks_domain.models.Task

sealed interface AllTasksEvents : EventsType {

    data object NavigateBack : AllTasksEvents
    data object GetAllTasks : AllTasksEvents
    data object TurnOnSelectionModeForDelete : AllTasksEvents
    data class DeleteTask(val task: List<Task>) : AllTasksEvents
    data class NavigateToCreatingTaskScreen(val id: Int?) : AllTasksEvents


}