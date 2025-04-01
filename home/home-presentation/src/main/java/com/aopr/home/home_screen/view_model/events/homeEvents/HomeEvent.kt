package com.aopr.home.home_screen.view_model.events.homeEvents

import com.aopr.shared_ui.util.events_type.EventsType

sealed interface HomeEvent:EventsType {
    data object NavigateToAllNotes : HomeEvent
   data object NavigateToAllTasks: HomeEvent
    data object NavigateToCreateNote: HomeEvent
    data object NavigateToCreateBookmark: HomeEvent
    data object NavigateToCalendarScreen: HomeEvent
    data object NavigateToCreateTask: HomeEvent
    data object NavigateToAllCategoriesOfBookmarks: HomeEvent
    data object NavigateToThemesByDrawer: HomeEvent
    data object LogOut: HomeEvent

}