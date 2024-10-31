package com.aopr.notes_presentation.view_model.events

import com.aopr.notes_domain.models.Note


sealed interface NotesEvent {
    data object SaveNote : NotesEvent
    data class DeleteNote(val note: Note) : NotesEvent
    data object NavigateToAllNotes : NotesEvent
    data class GetNoteById(val id: Int) : NotesEvent
    data class UpdateTittle(val tittle: String) : NotesEvent
    data class UpdateDescription(val description: String) : NotesEvent
}

sealed interface AllNotesEvent {
    data object GetAllNotes : AllNotesEvent
}
