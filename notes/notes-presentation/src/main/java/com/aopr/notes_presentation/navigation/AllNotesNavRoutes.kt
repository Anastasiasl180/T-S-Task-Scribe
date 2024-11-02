package com.aopr.notes_presentation.navigation

import com.aopr.notes_presentation.view_model.events.AllNotesEvent
import kotlinx.serialization.Serializable

@Serializable
sealed interface AllNotesNavRoutes {

    @Serializable
    data object CreatingNoteScreen:AllNotesNavRoutes

}