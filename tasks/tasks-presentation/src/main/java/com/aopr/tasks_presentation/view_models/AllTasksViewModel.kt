package com.aopr.tasks_presentation.view_models

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aopr.shared_domain.Responses
import com.aopr.shared_ui.util.ViewModelKit
import com.aopr.tasks_domain.interactors.TasksUseCase
import com.aopr.tasks_domain.models.Task
import com.aopr.tasks_presentation.events.all_tasks_events.AllTasksEvents
import com.aopr.tasks_presentation.events.all_tasks_events.AllTasksUiEvents
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class AllTasksViewModel(private val tasksUseCase: TasksUseCase) : ViewModelKit<AllTasksEvents,AllTasksUiEvents>() {

    private val _listOfTasks = mutableStateOf(emptyList<Task>())
    val listOfTasks: State<List<Task>> = _listOfTasks

    private val _isInSelectionMode = MutableStateFlow(false)
    val isInSelectedMode: StateFlow<Boolean> = _isInSelectionMode

    private val _selectedTasksToDelete = mutableStateListOf<Task>()
    val selectedTasksToDelete: List<Task> get() = _selectedTasksToDelete

    private val _events = MutableSharedFlow<AllTasksUiEvents>()
    val events = _events.asSharedFlow()

    init {
        onEvent(AllTasksEvents.GetAllTasks)
    }



    fun addTaskToDelete(task: Task) {
        _selectedTasksToDelete.add(task)
    }

    fun removeTaskFromDelete(task: Task) {
        _selectedTasksToDelete.remove(task)
    }

    fun cancelTaskFromDelete() {
        _selectedTasksToDelete.clear()
        _isInSelectionMode.value = false
    }
    fun deleteSelectedNotes() {
        if (_selectedTasksToDelete.isNotEmpty()) {
            val tasksToDelete = _selectedTasksToDelete.toList()
            onEvent(AllTasksEvents.DeleteTask(tasksToDelete))
            _selectedTasksToDelete.clear()
            _isInSelectionMode.value = false
        }

    }

    private fun getAllTasks() {
        tasksUseCase.getAllTasks().onEach { result ->
            when (result) {
                is Responses.Error -> {
                }

                is Responses.Loading -> {

                }

                is Responses.Success -> {
                    result.data?.collect { task ->
                        _listOfTasks.value = task
                    }
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun deleteTask(task: List<Task>) {
        tasksUseCase.deleteTask(task).onEach { result ->
            when (result) {
                is Responses.Error -> {
                    println(result.message.toString())
                }

                is Responses.Loading -> {

                }

                is Responses.Success -> {

                }
            }

        }.launchIn(viewModelScope)
    }


    override fun onEvent(event: AllTasksEvents) {
        when (event) {
            is AllTasksEvents.NavigateToCreatingTaskScreen -> {
                viewModelScope.launch {
                    _events.emit(
                        AllTasksUiEvents.NavigateToCreateTaskScreen(event.id)
                    )
                }
            }

            AllTasksEvents.GetAllTasks -> {
                viewModelScope.launch {
                    getAllTasks()
                }
            }

            is AllTasksEvents.DeleteTask -> {
                viewModelScope.launch {
                    deleteTask(event.task)
                }
            }

            AllTasksEvents.NavigateBack -> {
                viewModelScope.launch {
                    _events.emit(AllTasksUiEvents.NavigateBack)
                }
            }

            AllTasksEvents.TurnOnSelectionModeForDelete -> {
                if(_listOfTasks.value.isNotEmpty()){

                    _isInSelectionMode.value = !_isInSelectionMode.value
                }

            }
        }

    }
}