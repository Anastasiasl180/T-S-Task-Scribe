package com.example.calendar_presentation.view_model.events

import com.aopr.shared_ui.util.events_type.UiEventsType


sealed class CalendarUiEvents:UiEventsType{
    data class NavigateToCreateTaskScreen(val id:Int?):CalendarUiEvents()
    data object NavigateBack:CalendarUiEvents()
}