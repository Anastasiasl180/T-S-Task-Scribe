package com.example.bookmarks_presentation.events.main_events

sealed interface MainEvents {
    data class NavigateToCreateBookmark(val id:Int? = null):MainEvents
}