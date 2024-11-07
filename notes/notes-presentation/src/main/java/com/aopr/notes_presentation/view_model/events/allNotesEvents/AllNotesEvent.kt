package com.aopr.notes_presentation.view_model.events.allNotesEvents

import com.aopr.notes_domain.models.Note
import com.aopr.shared_ui.util.EventsType

sealed interface AllNotesEvent:EventsType {
    data object GetAllNotes : AllNotesEvent
    data class NavigateToCreateNoteScreen(val id:Int?):AllNotesEvent
    data class DeleteNote(val note: Note) : AllNotesEvent
    data class PinNote(val note: Note):AllNotesEvent
    data object TapNoteCardMessage:AllNotesEvent
}