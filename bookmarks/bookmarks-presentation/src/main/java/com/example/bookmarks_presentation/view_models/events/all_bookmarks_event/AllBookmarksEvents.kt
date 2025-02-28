package com.example.bookmarks_presentation.view_models.events.all_bookmarks_event

import com.aopr.shared_ui.util.events_type.EventsType
import com.example.bookmarks_domain.models.Bookmark
import com.example.bookmarks_presentation.view_models.events.all_bookmarks_in_category_event.AllBookmarksInCategoryEvents

sealed interface AllBookmarksEvents:EventsType {
    data object NavigateBack: AllBookmarksEvents
    data object GetAllBookmarks: AllBookmarksEvents
    data object TurnOnSelectionModeForDelete: AllBookmarksEvents
    data object NavigateToCreateBookmarkScreen: AllBookmarksEvents
    data class NavigateToBookmarkById(val id:Int?): AllBookmarksEvents
    data class DeleteBookmark(val bookmark: Bookmark): AllBookmarksEvents
    data object DeleteSeveralBookmarks: AllBookmarksEvents
    data class RemoveBookmarkForDeletion(val bookmark: Bookmark):AllBookmarksEvents
    data class AddBookmarkForDeletion(val bookmark: Bookmark): AllBookmarksEvents
}