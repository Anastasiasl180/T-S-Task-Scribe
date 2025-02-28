package com.aopr.shared_ui.util.global_view_model.events

sealed class GlobalUiEvents {
        data object NavigateToHomeScreen : GlobalUiEvents()
        data object NavigateToAiScreen : GlobalUiEvents()
        data object NavigateToDashBoardScreen : GlobalUiEvents()
    }