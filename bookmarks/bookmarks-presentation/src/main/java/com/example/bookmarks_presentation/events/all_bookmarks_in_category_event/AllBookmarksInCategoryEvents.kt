package com.example.bookmarks_presentation.events.all_bookmarks_in_category_event

import com.example.bookmarks_domain.models.Bookmark
import com.example.bookmarks_presentation.events.all_bookmarks_event.AllBookmarksEvents

sealed interface AllBookmarksInCategoryEvents {
    data class GetAllBookmarksByCategoryId(val id:Int?): AllBookmarksInCategoryEvents
    data object NavigateToCreateBookmarkWithCategoryId: AllBookmarksInCategoryEvents
    data class DeleteBookmark(val bookmark: Bookmark): AllBookmarksInCategoryEvents
}