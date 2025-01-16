package com.aopr.notes_presentation.view_model.events.creating_note_events

import com.aopr.shared_ui.util.events_type.EventsType

sealed interface CreatingNoteEvents : EventsType {
    data class UpdateDescription(val description: String) : CreatingNoteEvents
    data class UpdateTittle(val tittle: String) : CreatingNoteEvents
    data class GetNoteById(val id: Int) : CreatingNoteEvents
    data object NavigateToAllNotes : CreatingNoteEvents
    data object SaveNote : CreatingNoteEvents
    data object PinNote : CreatingNoteEvents
}