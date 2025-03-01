package com.aopr.shared_ui.util.global_view_model.events

import com.aopr.shared_domain.colors_for_theme.Themes

sealed interface GlobalEvents {
        data class ToMoveBottomBar(val move: Boolean) : GlobalEvents
        data object NavigateToHomeScreen : GlobalEvents
        data object NavigateToAiScreen : GlobalEvents
        data object NavigateToDashBoardScreen : GlobalEvents
        data object NavigateBack : GlobalEvents
        data object ShowBottomBar : GlobalEvents
        data object SetFirstLaunchToFalse : GlobalEvents
        data class ChosenTheme(val theme: Themes) : GlobalEvents
    }