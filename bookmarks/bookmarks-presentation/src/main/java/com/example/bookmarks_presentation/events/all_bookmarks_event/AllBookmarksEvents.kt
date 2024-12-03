package com.example.bookmarks_presentation.events.all_bookmarks_event

import com.example.bookmarks_domain.models.Bookmark

sealed interface AllBookmarksEvents {
    data class NavigateToBookmarkById(val id:Int?): AllBookmarksEvents
    data object NavigateBack: AllBookmarksEvents
    data object GetAllBookmarks: AllBookmarksEvents
    data class DeleteBookmark(val bookmark: Bookmark): AllBookmarksEvents

}