package com.example.bookmarks_presentation.view_models.events.categories_events

sealed class UiCategoriesEvents {
    data class NavigateToCreateBookmark(val id:Int?): UiCategoriesEvents()
    data object NavigateToAllBookMarks: UiCategoriesEvents()
    data object NavigateBack: UiCategoriesEvents()
    data class NavigateToBookmarksByCategoryId(val id:Int): UiCategoriesEvents()
}