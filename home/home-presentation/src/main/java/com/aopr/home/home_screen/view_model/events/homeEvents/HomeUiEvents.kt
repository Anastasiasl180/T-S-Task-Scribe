package com.aopr.home.home_screen.view_model.events.homeEvents

import com.aopr.shared_ui.util.events_type.UiEventsType


sealed class HomeUiEvents:UiEventsType {
    data object NavigateToAllNotesScreen: HomeUiEvents()
    data object NavigateToCreateNotesScreen: HomeUiEvents()
    data object NavigateToCalendarScreen: HomeUiEvents()
    data object NavigateToCreateTaskScreen: HomeUiEvents()
    data object NavigateToCreateBookmarkScreen: HomeUiEvents()
    data object NavigateToAllTasks: HomeUiEvents()
    data object NavigateToAllCategoriesOfBookmarks: HomeUiEvents()
    data object NavigateToThemesByDrawer: HomeUiEvents()
    data object NavigateToRegistrationScreen: HomeUiEvents()

}