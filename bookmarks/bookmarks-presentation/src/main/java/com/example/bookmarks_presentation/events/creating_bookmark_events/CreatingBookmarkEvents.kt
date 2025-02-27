package com.example.bookmarks_presentation.events.creating_bookmark_events

import com.aopr.shared_ui.util.events_type.EventsType
import com.example.bookmarks_presentation.navigation.BookmarksRoutesInfo

sealed interface CreatingBookmarkEvents:EventsType {
    data object CancelFile : CreatingBookmarkEvents
    data object CancelLink : CreatingBookmarkEvents
    data object NavigateBack: CreatingBookmarkEvents
    data object GetAllCategories: CreatingBookmarkEvents
    data object ExpandDropDownMenu: CreatingBookmarkEvents
    data class AddFile(val uri: String) : CreatingBookmarkEvents
    data class AddLink(val url: String) : CreatingBookmarkEvents
    data class SelectCategory(val id:Int?): CreatingBookmarkEvents
    data class SaveBookmark(val userId:String) : CreatingBookmarkEvents
    data class UpdateTittleOfBookmark(val tittle: String) : CreatingBookmarkEvents
    data class GetBookmarkById(val bookmarkInfo: BookmarksRoutesInfo) : CreatingBookmarkEvents
}