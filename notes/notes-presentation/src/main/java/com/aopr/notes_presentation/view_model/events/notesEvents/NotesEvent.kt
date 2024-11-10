package com.aopr.notes_presentation.view_model.events.notesEvents

import com.aopr.shared_ui.util.events_type.EventsType

sealed interface NotesEvent:EventsType {
    data object SaveNote : NotesEvent
    data object NavigateToAllNotes : NotesEvent
    data class UpdateTittle(val tittle: String) : NotesEvent
    data class UpdateDescription(val description: String) : NotesEvent
}