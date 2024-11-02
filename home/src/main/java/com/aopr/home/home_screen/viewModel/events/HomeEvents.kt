package com.aopr.home.home_screen.viewModel.events

sealed interface HomeEvent{
    data object NavigateToAllNotes:HomeEvent
}