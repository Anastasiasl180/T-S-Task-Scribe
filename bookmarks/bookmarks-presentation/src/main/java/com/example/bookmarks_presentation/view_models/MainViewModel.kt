package com.example.bookmarks_presentation.view_models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aopr.shared_ui.MainViewModel
import com.example.bookmarks_presentation.events.main_events.MainEvents
import com.example.bookmarks_presentation.events.main_events.UiMainEvents
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class MainViewModel:ViewModel() {

    private val _event = MutableSharedFlow<UiMainEvents>()
    val event = _event.asSharedFlow()

    fun onEvent(event:MainEvents){
        when(event){
            is MainEvents.NavigateToCreateBookmark -> {
                viewModelScope.launch {
                    _event.emit(UiMainEvents.NavigateToCreateBookmark(event.id))
                }
            }
        }

    }



}