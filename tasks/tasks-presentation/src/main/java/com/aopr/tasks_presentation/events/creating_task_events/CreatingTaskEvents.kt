package com.aopr.tasks_presentation.events.creating_task_events

import com.aopr.tasks_domain.models.ImportanceOfTask
import java.time.LocalDate
import java.time.LocalTime

sealed interface CreatingTaskEvents {
    data class GetTasksByDate(val date: LocalDate) : CreatingTaskEvents
    data object LoadDatesWithTask : CreatingTaskEvents
    data object ShowCalendar : CreatingTaskEvents
    data object HideCalendar : CreatingTaskEvents
    data object ShowClock : CreatingTaskEvents
    data object HideClock : CreatingTaskEvents
    data class GetTakById(val id: Int?) : CreatingTaskEvents
    data object SaveTask : CreatingTaskEvents
    data class UpdateTittleOfTask(val tittle: String) : CreatingTaskEvents
    data class UpdateDescriptionOfTask(val description: String) : CreatingTaskEvents
    data class UpdatePriorityOfTask(val priority: ImportanceOfTask) : CreatingTaskEvents
    data class UpdateIsDoneTask(val isDone: Boolean) : CreatingTaskEvents
    data class UpdateDateOfTask(val date: LocalDate?) : CreatingTaskEvents
    data class UpdateTimeOfTask(val time: LocalTime?) : CreatingTaskEvents
    data object AddTextFieldForSubTask : CreatingTaskEvents
    data class UpdateTempSubTaskDescription(val index: Int, val description: String) : CreatingTaskEvents
    data class UpdateTempSubTaskIsDone(val index: Int, val isDone: Boolean) : CreatingTaskEvents
    data object NavigateToBack : CreatingTaskEvents
}