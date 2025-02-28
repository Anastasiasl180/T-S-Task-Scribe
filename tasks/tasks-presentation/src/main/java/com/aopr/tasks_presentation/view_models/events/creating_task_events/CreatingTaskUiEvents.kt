package com.aopr.tasks_presentation.view_models.events.creating_task_events

import com.aopr.shared_ui.util.events_type.UiEventsType

sealed class CreatingTaskUiEvents:UiEventsType {
    data object NavigateToBack: CreatingTaskUiEvents()
}