package com.example.bookmarks_presentation.events.creating_bookmark_events

import com.aopr.shared_ui.util.events_type.UiEventsType

sealed class CreatingBookmarkUiEvents:UiEventsType {
    data object NavigateBack:CreatingBookmarkUiEvents()
}