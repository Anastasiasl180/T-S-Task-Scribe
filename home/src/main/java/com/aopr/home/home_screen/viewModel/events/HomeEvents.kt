package com.aopr.home.home_screen.viewModel.events

import com.aopr.shared_ui.util.events_type.EventsType

sealed interface HomeEvent:EventsType{
    data object NavigateToAllNotes:HomeEvent
}