package com.example.bookmarks_presentation.events.creating_bookmark_events

import com.example.bookmarks_presentation.navigation.BookmarksRoutesInfo

sealed interface CreatingBookmarkEvents {
    data class GetBookmarkById(val bookmarkInfo: BookmarksRoutesInfo) : CreatingBookmarkEvents
    data object GetAllCategories: CreatingBookmarkEvents
    data class SaveBookmark(val userId:String) : CreatingBookmarkEvents
    data class OpenFile(val uri: String) : CreatingBookmarkEvents
    data object CancelFile : CreatingBookmarkEvents
    data class AddFile(val uri: String) : CreatingBookmarkEvents
    data class AddLink(val url: String) : CreatingBookmarkEvents
    data object CancelLink : CreatingBookmarkEvents
    data class OpenLink(val url: String) : CreatingBookmarkEvents
    data class UpdateTittleOfBookmark(val tittle: String) : CreatingBookmarkEvents
    data object ExpandDropDownMenu: CreatingBookmarkEvents
    data class SelectCategory(val id:Int?): CreatingBookmarkEvents

}