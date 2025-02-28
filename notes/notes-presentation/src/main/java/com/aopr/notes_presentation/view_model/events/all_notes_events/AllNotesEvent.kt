package com.aopr.notes_presentation.view_model.events.all_notes_events

import com.aopr.notes_domain.models.Note
import com.aopr.shared_ui.util.events_type.EventsType

sealed interface AllNotesEvent: EventsType {
    data class PinNote(val note: Note):AllNotesEvent
    data object DeleteSeveralNotes:AllNotesEvent
    data class RemoveNoteForDeletion(val note: Note):AllNotesEvent
    data class AddNoteForDeletion(val note: Note): AllNotesEvent
    data class NavigateToCreateNoteScreen(val id:Int?):AllNotesEvent
    data object NavigateBack:AllNotesEvent
    data object GetAllNotes : AllNotesEvent
    data object TapNoteCardMessage:AllNotesEvent
    data object TurnOnSelectionModeForDelete:AllNotesEvent
}