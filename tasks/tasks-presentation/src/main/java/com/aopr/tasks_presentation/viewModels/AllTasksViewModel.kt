package com.aopr.tasks_presentation.viewModels

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.aopr.shared_domain.Responses
import com.aopr.tasks_domain.interactors.TasksUseCase
import com.aopr.tasks_domain.models.Task
import com.aopr.tasks_presentation.events.all_tasks_events.AllTasksEvents
import com.aopr.tasks_presentation.events.all_tasks_events.AllTasksUiEvents
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class AllTasksViewModel(private val tasksUseCase: TasksUseCase) : ViewModel() {

    private val _listOfTasks = mutableStateOf<List<Task>?>(null)
    val listOfTasks: State<List<Task>?> = _listOfTasks

    private val _events = MutableSharedFlow<AllTasksUiEvents>()
    val events = _events.asSharedFlow()

    init {
        onEvent(AllTasksEvents.GetAllTasks)
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

    private fun deleteTask(task: Task) {
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


    fun onEvent(event: AllTasksEvents) {
        when (event) {
            is AllTasksEvents.NavigateToCreatingTaskScreen -> {
                viewModelScope.launch {
                    _events.emit(
                        AllTasksUiEvents.NavigateToCreateTaskScreen(event.id))
                }
            }

            AllTasksEvents.GetAllTasks -> {
                viewModelScope.launch {
                    getAllTasks()
                }
            }

            is AllTasksEvents.DeleteTask -> {
                viewModelScope.launch {
                  //  _events.emit(AllTasksUiEvents.DeleteTask(event.task))
                    deleteTask(event.task)
                }
            }
        }

    }
}