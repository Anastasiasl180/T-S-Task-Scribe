package com.aopr.tasks_presentation.events.creating_task_events

import com.aopr.shared_ui.util.events_type.EventsType
import com.aopr.tasks_domain.models.ImportanceOfTask
import com.aopr.tasks_domain.models.Task
import com.aopr.tasks_presentation.events.all_tasks_events.AllTasksEvents
import java.time.LocalDate
import java.time.LocalTime

sealed interface CreatingTaskEvents: EventsType {
    data class GetTasksByDate(val date: LocalDate) : CreatingTaskEvents
    data object LoadDatesWithTask : CreatingTaskEvents
    data object ShowCalendarForToBeDone : CreatingTaskEvents
    data object ShowCalendarForReminder : CreatingTaskEvents
    data class ShowCalendarForSubTask(val index:Int):CreatingTaskEvents
    data object HideCalendar : CreatingTaskEvents
    data object ShowClockForTaskReminder : CreatingTaskEvents
    data class ShowClockForSubTaskReminder(val index:Int) : CreatingTaskEvents
    data object HideClock : CreatingTaskEvents
    data class GetTakById(val id: Int?) : CreatingTaskEvents
    data object SaveTask : CreatingTaskEvents
    data class UpdateTittleOfTask(val tittle: String) : CreatingTaskEvents
    data class UpdateDescriptionOfTask(val description: String) : CreatingTaskEvents
    data class UpdatePriorityOfTask(val priority: ImportanceOfTask) : CreatingTaskEvents
    data class UpdateIsDoneTask(val isDone: Boolean) : CreatingTaskEvents
    data class UpdateDateForSubtask(val date:LocalDate?):CreatingTaskEvents
    data class UpdateTimeForSubTask(val time:LocalTime?):CreatingTaskEvents
    data class UpdateDateOfTaskForReminder(val date: LocalDate?) : CreatingTaskEvents
    data class UpdateDateOfTaskToBeDone(val date: LocalDate) : CreatingTaskEvents
    data class UpdateTimeOfTask(val time: LocalTime?) : CreatingTaskEvents
    data object AddTextFieldForSubTask : CreatingTaskEvents
    data class RemoveTextFieldForSubTask(val task: Task?,val index:Int) : CreatingTaskEvents
    data class UpdateTempSubTaskDescription(val index: Int, val description: String) : CreatingTaskEvents
    data class UpdateTempSubTaskIsDone(val index: Int, val isDone: Boolean) : CreatingTaskEvents
    data object NavigateToBack : CreatingTaskEvents
    data object CleanTimeOfTaskReminder: CreatingTaskEvents
    data object CleanDateOfTaskReminder: CreatingTaskEvents
    data class CleanDataOfSubTask(val index:Int): CreatingTaskEvents
    data class CleanTimeOfSubtask(val index:Int): CreatingTaskEvents
}