package com.aopr.tasks_presentation.view_models.events.creating_task_events

import com.aopr.shared_ui.util.events_type.EventsType
import com.aopr.tasks_domain.models.ImportanceOfTask
import com.aopr.tasks_domain.models.Task
import java.time.LocalDate
import java.time.LocalTime

sealed interface CreatingTaskEvents : EventsType {

    data object SaveTask : CreatingTaskEvents
    data object HideClock : CreatingTaskEvents
    data object HideCalendar : CreatingTaskEvents
    data object NavigateToBack : CreatingTaskEvents
    data object LoadDatesWithTask : CreatingTaskEvents
    data object CleanTimeOfTaskReminder : CreatingTaskEvents
    data object CleanDateOfTaskReminder : CreatingTaskEvents
    data object AddTextFieldForSubTask : CreatingTaskEvents
    data object ShowCalendarForToBeDone : CreatingTaskEvents
    data object ShowCalendarForReminder : CreatingTaskEvents
    data object ShowClockForTaskReminder : CreatingTaskEvents

    data class GetTakById(val id: Int?) : CreatingTaskEvents
    data class CleanDataOfSubTask(val index: Int) : CreatingTaskEvents
    data class CleanTimeOfSubtask(val index: Int) : CreatingTaskEvents
    data class GetTasksByDate(val date: LocalDate) : CreatingTaskEvents
    data class ShowCalendarForSubTask(val index: Int) : CreatingTaskEvents
    data class UpdateIsDoneOfTask(val isDone: Boolean) : CreatingTaskEvents
    data class UpdateTimeOfTask(val time: LocalTime?) : CreatingTaskEvents
    data class UpdateTittleOfTask(val tittle: String) : CreatingTaskEvents
    data class UpdateDateForSubtask(val date: LocalDate?) : CreatingTaskEvents
    data class UpdateTimeForSubTask(val time: LocalTime?) : CreatingTaskEvents
    data class ShowClockForSubTaskReminder(val index: Int) : CreatingTaskEvents
    data class UpdateDateOfTaskToBeDone(val date: LocalDate) : CreatingTaskEvents
    data class UpdateDescriptionOfTask(val description: String) : CreatingTaskEvents
    data class UpdateDateOfTaskForReminder(val date: LocalDate?) : CreatingTaskEvents
    data class UpdatePriorityOfTask(val priority: ImportanceOfTask) : CreatingTaskEvents
    data class RemoveTextFieldForSubTask(val task: Task?, val index: Int) : CreatingTaskEvents
    data class UpdateTempSubTaskIsDone(val index: Int, val isDone: Boolean) : CreatingTaskEvents
    data class UpdateTempSubTaskDescription(val index: Int, val description: String) :
        CreatingTaskEvents
}