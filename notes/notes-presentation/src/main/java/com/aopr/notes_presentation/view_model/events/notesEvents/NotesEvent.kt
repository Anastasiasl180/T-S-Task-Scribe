package com.aopr.notes_presentation.view_model.events.notesEvents

import com.aopr.notes_domain.models.Note
import com.aopr.shared_ui.util.events_type.EventsType

sealed interface NotesEvent:EventsType {
    data object SaveNote : NotesEvent
    data object NavigateToAllNotes : NotesEvent
    data class UpdateTittleOfNote(val tittle: String) : NotesEvent
    data class UpdateDescriptionOfNote(val description: String) : NotesEvent
    data class UpdateTittleOFTask(val tittle: String) : NotesEvent
    data class UpdateDescriptionOfTask(val description: String) : NotesEvent
    data object SaveTask:NotesEvent
    data object NavigateToAllTasks:NotesEvent
    data object NavigateToAllCategoriesOfBookmarks:NotesEvent

}