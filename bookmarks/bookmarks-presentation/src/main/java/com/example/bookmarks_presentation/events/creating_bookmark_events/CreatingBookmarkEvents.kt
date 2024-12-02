package com.example.bookmarks_presentation.events.creating_bookmark_events

sealed interface CreatingBookmarkEvents {
    data class GetBookmarkById(val id: Int?) : CreatingBookmarkEvents
    data object SaveBookmark:CreatingBookmarkEvents
    data class UpdateTittleOfBookmark(val tittle:String):CreatingBookmarkEvents
}