package com.example.calendar_presentation.view_model.events

import com.aopr.shared_ui.util.events_type.EventsType
import java.time.LocalDate

sealed interface CalendarEvents:EventsType {
    data object LoadDatesWithTask : CalendarEvents
    data class GetTasksByDate(val date: LocalDate) : CalendarEvents
    data class NavigateToCreateNewTaskOrExhistingTask(val id:Int?):CalendarEvents
    data object NavigateBack:CalendarEvents

}