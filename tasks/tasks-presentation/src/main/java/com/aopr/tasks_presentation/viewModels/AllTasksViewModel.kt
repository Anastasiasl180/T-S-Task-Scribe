package com.aopr.tasks_presentation.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aopr.tasks_presentation.events.all_tasks_events.AllTasksEvents
import com.aopr.tasks_presentation.events.all_tasks_events.AllTasksUiEvents
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class AllTasksViewModel:ViewModel() {

    private val _events = MutableSharedFlow<AllTasksUiEvents>()
    val events = _events.asSharedFlow()

    fun onEvent(event: AllTasksEvents){
        when(event){
            is AllTasksEvents.NavigateToCreatingTaskScreen -> {
                viewModelScope.launch {
                    _events.emit(AllTasksUiEvents.NavigateToCreateTaskScreen(event.id))
                }
            }
        }
    }

}