package com.example.calendar_presentation.view_model

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.viewModelScope
import com.aopr.shared_domain.Responses
import com.aopr.shared_ui.util.ViewModelKit
import com.aopr.tasks_domain.interactors.TasksUseCase
import com.aopr.tasks_domain.models.Task
import com.example.calendar_presentation.view_model.events.CalendarEvents
import com.example.calendar_presentation.view_model.events.CalendarUiEvents
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel
import java.time.LocalDate

@KoinViewModel
class CalendarViewModel(private val tasksUseCase: TasksUseCase) :
    ViewModelKit<CalendarEvents, CalendarUiEvents>() {

    private val _datesWithTasks = mutableStateListOf<LocalDate?>()
    val datesWithTasks: List<LocalDate?> = _datesWithTasks

    private val _tasksByDate = mutableStateListOf<Task>()
    val tasksByDate: List<Task> = _tasksByDate

    private val _event = MutableSharedFlow<CalendarUiEvents>()
    val event = _event.asSharedFlow()

    init {
        onEvent(CalendarEvents.LoadDatesWithTask)
    }


    private fun getTasksByDate(date: LocalDate) {
        tasksUseCase.getTasksByDate(date).onEach { result ->
            when (result) {
                is Responses.Error -> {
                    hideInfoBar()
                    result.message?.let { showShortInfoBar(it, 2) }
                }

                is Responses.Loading -> {

                }

                is Responses.Success -> {
                    result.data?.collect { list ->
                        _tasksByDate.clear()
                        _tasksByDate.addAll(list)
                    }
                }
            }

        }.launchIn(viewModelScope)
    }

    private fun getDatesWithTasks() {
        tasksUseCase.getAllTasks().onEach { result ->
            when (result) {
                is Responses.Error -> {

                }

                is Responses.Loading -> {

                }

                is Responses.Success -> {
                    result.data?.collect { list ->
                        val dates = list.map { task ->
                            task.dateOfTaskToBeDone
                        }
                        _datesWithTasks.addAll(dates)
                    }
                }
            }

        }.launchIn(viewModelScope)
    }


    override fun onEvent(event: CalendarEvents) {
        when (event) {
            CalendarEvents.NavigateBack -> {
                viewModelScope.launch {
                    _event.emit(CalendarUiEvents.NavigateBack)
                }
            }

            is CalendarEvents.NavigateToCreateNewTaskOrExhistingTask -> {
                viewModelScope.launch {
                    _event.emit(CalendarUiEvents.NavigateToCreateTaskScreen(event.id))
                }
            }

            is CalendarEvents.GetTasksByDate -> {
                viewModelScope.launch {
                    getTasksByDate(event.date)
                }
            }

            CalendarEvents.LoadDatesWithTask -> {
                viewModelScope.launch {
                    getDatesWithTasks()
                }
            }
        }
    }


}