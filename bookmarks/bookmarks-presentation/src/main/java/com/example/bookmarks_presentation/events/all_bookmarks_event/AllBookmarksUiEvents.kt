package com.example.bookmarks_presentation.events.all_bookmarks_event

sealed class AllBookmarksUiEvents {
    data class NavigateToBookmarkById(val id:Int?): AllBookmarksUiEvents()
}