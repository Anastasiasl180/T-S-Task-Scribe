package com.aopr.notes_presentation.view_model.events.notesEvents

sealed interface NotesEvent {
    data object SaveNote : NotesEvent
    data object NavigateToAllNotes : NotesEvent
    data class UpdateTittle(val tittle: String) : NotesEvent
    data class UpdateDescription(val description: String) : NotesEvent
}