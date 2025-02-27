package com.example.bookmarks_presentation.events.categories_events

sealed class UiCategoriesEvents {
    data class NavigateToCreateBookmark(val id:Int?):UiCategoriesEvents()
    data object NavigateToAllBookMarks:UiCategoriesEvents()
    data class NavigateToBookmarksByCategoryId(val id:Int): UiCategoriesEvents()
}