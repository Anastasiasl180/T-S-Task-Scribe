package com.example.bookmarks_presentation.events.all_bookmarks_event

import com.aopr.shared_ui.util.events_type.EventsType
import com.example.bookmarks_domain.models.Bookmark

sealed interface AllBookmarksEvents:EventsType {
    data object NavigateBack: AllBookmarksEvents
    data object GetAllBookmarks: AllBookmarksEvents
    data object TurnOnSelectionModeForDelete:AllBookmarksEvents
    data object NavigateToCreateBookmarkScreen: AllBookmarksEvents
    data class NavigateToBookmarkById(val id:Int?): AllBookmarksEvents
    data class DeleteBookmark(val bookmark: Bookmark): AllBookmarksEvents
    data class DeleteSeveralBookmarks(val bookmarks: List<Bookmark>): AllBookmarksEvents
}