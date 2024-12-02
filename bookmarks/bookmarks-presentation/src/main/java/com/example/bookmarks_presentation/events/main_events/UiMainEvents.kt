package com.example.bookmarks_presentation.events.main_events

sealed class UiMainEvents {
    data class NavigateToCreateBookmark(val id:Int?):UiMainEvents()
    data object NavigateToAllBookMarks:UiMainEvents()
}