package com.aopr.home.home_screen.view_model.events.homeEvents

import com.aopr.shared_ui.util.events_type.EventsType

sealed interface HomeEvent:EventsType {
    data object SaveNote : HomeEvent
    data object NavigateToAllNotes : HomeEvent
    data class UpdateTittleOfNote(val tittle: String) : HomeEvent
    data class UpdateDescriptionOfNote(val description: String) : HomeEvent
    data class UpdateTittleOFTask(val tittle: String) : HomeEvent
    data class UpdateDescriptionOfTask(val description: String) : HomeEvent
    data object SaveTask: HomeEvent
    data object NavigateToAllTasks: HomeEvent
    data object NavigateToAllCategoriesOfBookmarks: HomeEvent
    data object NavigateToThemesByDrawer: HomeEvent
    data object LogOut: HomeEvent

}