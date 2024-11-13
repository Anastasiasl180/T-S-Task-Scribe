package com.aopr.tasks_presentation.events.creating_task_events

sealed interface CreatingTaskEvents {
    data class GetTakById(val id:Int?):CreatingTaskEvents
    data object SaveTask:CreatingTaskEvents
    data class UpdateTittle(val tittle:String):CreatingTaskEvents
    data class UpdateDescription(val description:String):CreatingTaskEvents
    data object NavigateToBack:CreatingTaskEvents
}