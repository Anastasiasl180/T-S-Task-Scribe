package com.aopr.notes_presentation.view_model.events.CreatingNoteEvents

import com.aopr.shared_ui.util.events_type.EventsType

sealed interface CreatingNoteEvent:EventsType{
    data class GetNoteById(val id:Int?):CreatingNoteEvent
    data object SaveNote:CreatingNoteEvent
    data class UpdateTittle(val tittle:String):CreatingNoteEvent
    data class UpdateDescription(val description:String):CreatingNoteEvent
    data object NavigateToBack:CreatingNoteEvent
    data object PinNote:CreatingNoteEvent
}