package com.aopr.tasks_presentation.view_models.events.all_tasks_events

import android.icu.text.IDNA.Info
import com.aopr.shared_ui.util.events_type.EventsType
import com.aopr.tasks_domain.models.Task

sealed interface AllTasksEvents : EventsType {

    data object NavigateBack : AllTasksEvents
    data object GetAllTasks : AllTasksEvents
    data object TurnOnSelectionModeForDelete : AllTasksEvents
    data object DeleteSeveralCategories: AllTasksEvents
    data class RemoveCategoryForDeletion(val task: Task):  AllTasksEvents
    data class AddCategoryForDeletion(val task: Task):  AllTasksEvents
    data class NavigateToCreatingTaskScreen(val id: Int?) : AllTasksEvents


}