package com.aopr.tasks_presentation.events.creating_task_events

sealed class CreatingTaskUiEvents {
    data object NavigateToBack:CreatingTaskUiEvents()
}