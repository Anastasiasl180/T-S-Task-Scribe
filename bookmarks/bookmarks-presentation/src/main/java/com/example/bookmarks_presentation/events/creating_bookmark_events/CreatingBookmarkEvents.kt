package com.example.bookmarks_presentation.events.creating_bookmark_events

sealed interface CreatingBookmarkEvents {
    data class GetBookmarkById(val id: Int?) : CreatingBookmarkEvents
    data object SaveBookmark : CreatingBookmarkEvents
    data class OpenFile(val uri: String) : CreatingBookmarkEvents
    data object CancelFile : CreatingBookmarkEvents
    data class AddFile(val uri: String) : CreatingBookmarkEvents
    data class AddLink(val url: String) : CreatingBookmarkEvents
    data object CancelLink : CreatingBookmarkEvents
    data class OpenLink(val url: String) : CreatingBookmarkEvents
    data class UpdateTittleOfBookmark(val tittle: String) : CreatingBookmarkEvents

}