package com.aopr.notes_presentation.view_model.events

import com.aopr.notes_domain.models.Note


sealed interface NotesEvent {
    data object SaveNote : NotesEvent
    data object NavigateToAllNotes : NotesEvent
    data class UpdateTittle(val tittle: String) : NotesEvent
    data class UpdateDescription(val description: String) : NotesEvent
}

sealed interface AllNotesEvent {
    data object GetAllNotes : AllNotesEvent
    data class NavigateToCreateNoteScreen(val id:Int?):AllNotesEvent
    data class DeleteNote(val note: Note) : AllNotesEvent
    data class PinNote(val note:Note):AllNotesEvent
}
sealed interface CreatingNoteEvent{
    data class GetNoteById(val id:Int?):CreatingNoteEvent
    data object SaveNote:CreatingNoteEvent
    data class UpdateTittle(val tittle:String):CreatingNoteEvent
    data class UpdateDescription(val description:String):CreatingNoteEvent
    data object NavigateToBack:CreatingNoteEvent
    data object PinNote:CreatingNoteEvent
}
