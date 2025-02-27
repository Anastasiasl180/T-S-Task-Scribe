package com.example.bookmarks_presentation.events.all_bookmarks_in_category_event

sealed class AllBookmarksInCategoryUiEvents {
    data object NavigateBack: AllBookmarksInCategoryUiEvents()
    data class NavigateToBookmarkById(val id:Int):AllBookmarksInCategoryUiEvents()
    data class NavigateToCreateBookmarkWithCategoryId(val id:Int): AllBookmarksInCategoryUiEvents()

}