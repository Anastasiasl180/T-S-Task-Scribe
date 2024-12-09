package com.aopr.home.home_screen.viewModel.events.homeEvents

import com.aopr.shared_ui.util.events_type.UiEventsType


sealed class HomeUiEvents:UiEventsType {
    data object NavigateToAllNotesScreen: HomeUiEvents()
    data object NavigateToAllTasks:HomeUiEvents()
    data object NavigateToAllCategoriesOfBookmarks:HomeUiEvents()
    data object NavigateToThemesByDrawer: HomeUiEvents()
}