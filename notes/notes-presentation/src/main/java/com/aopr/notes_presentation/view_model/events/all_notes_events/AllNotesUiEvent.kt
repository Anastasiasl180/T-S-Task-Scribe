package com.aopr.notes_presentation.view_model.events.all_notes_events

import com.aopr.shared_ui.util.events_type.UiEventsType

sealed class AllNotesUiEvent: UiEventsType {
    data class NavigateToCreateNoteScreen(val id: Int?) : AllNotesUiEvent()
    data object NavigateBack : AllNotesUiEvent()
}