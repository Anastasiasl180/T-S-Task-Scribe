package com.aopr.notes_presentation.view_model.events.CreatingNoteEvents

import com.aopr.shared_ui.util.events_type.UiEventsType

sealed class CreatingNoteUiEvents:UiEventsType {
        data object NavigateBack : CreatingNoteUiEvents()
    }