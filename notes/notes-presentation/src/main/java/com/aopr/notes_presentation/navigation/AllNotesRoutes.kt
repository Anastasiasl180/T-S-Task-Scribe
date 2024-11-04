package com.aopr.notes_presentation.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface AllNotesRoutes {
    @Serializable
    data class CreatingNoteScreen(val id:Int?):AllNotesRoutes
}