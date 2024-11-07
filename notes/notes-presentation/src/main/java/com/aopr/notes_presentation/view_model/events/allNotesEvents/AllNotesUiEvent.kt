package com.aopr.notes_presentation.view_model.events.allNotesEvents

import com.aopr.shared_ui.util.EventsType
import com.aopr.shared_ui.util.UiEventsType

sealed class AllNotesUiEvent:UiEventsType {
    data class NavigateToCreateNoteScreen(val id: Int?) : AllNotesUiEvent()
}