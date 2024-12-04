package com.example.bookmarks_presentation.events.all_bookmarks_in_category_event

sealed interface AllBookmarksInCategoryEvents {
    data class GetAllBookmarksByCategoryId(val id:Int?): AllBookmarksInCategoryEvents
}