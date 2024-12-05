package com.example.bookmarks_presentation.events.all_bookmarks_in_category_event

sealed class AllBookmarksInCategoryUiEvents {
    data class NavigateToCreateBookmarkWithCategoryId(val id:Int): AllBookmarksInCategoryUiEvents()
}