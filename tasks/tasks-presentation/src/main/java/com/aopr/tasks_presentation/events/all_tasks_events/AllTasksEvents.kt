package com.aopr.tasks_presentation.events.all_tasks_events

import android.icu.text.IDNA.Info

sealed interface AllTasksEvents {
    data object GetAllTasks : AllTasksEvents
    data class NavigateToCreatingTaskScreen(val id: Int?) : AllTasksEvents


}