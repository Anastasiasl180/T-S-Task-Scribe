package com.aopr.notes_presentation.view_model.events.notesEvents

import com.aopr.shared_ui.util.events_type.UiEventsType


sealed class NotesUiEvents:UiEventsType {
    data object NavigateToAllNotesScreen: NotesUiEvents()
    data object NavigateToAllTasks:NotesUiEvents()
}