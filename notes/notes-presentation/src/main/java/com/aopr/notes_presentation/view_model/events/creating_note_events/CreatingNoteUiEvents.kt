package com.aopr.notes_presentation.view_model.events.creating_note_events

import com.aopr.shared_ui.util.events_type.UiEventsType

sealed class CreatingNoteUiEvents : UiEventsType {
    data object NavigateToAllNotes : CreatingNoteUiEvents()
}