package com.example.bookmarks_presentation.view_models.events.all_bookmarks_event

import com.aopr.shared_ui.util.events_type.UiEventsType

sealed class AllBookmarksUiEvents:UiEventsType {
    data object NavigateBack: AllBookmarksUiEvents()
    data object NavigateToCreateBookmarkScreen: AllBookmarksUiEvents()
    data class NavigateToBookmarkById(val id:Int?): AllBookmarksUiEvents()
}