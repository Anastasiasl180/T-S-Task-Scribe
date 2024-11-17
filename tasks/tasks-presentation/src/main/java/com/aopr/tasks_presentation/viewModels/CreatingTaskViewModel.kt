package com.aopr.tasks_presentation.viewModels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aopr.shared_domain.Responses
import com.aopr.tasks_domain.interactors.TasksUseCase
import com.aopr.tasks_domain.models.ImportanceOfTask
import com.aopr.tasks_domain.models.Subtasks
import com.aopr.tasks_domain.models.Task
import com.aopr.tasks_presentation.events.creating_task_events.CreatingTaskEvents
import com.aopr.tasks_presentation.events.creating_task_events.CreatingTaskUiEvents
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel
import java.time.LocalDate
import java.time.LocalTime

@KoinViewModel
class CreatingTaskViewModel(private val tasksUseCase: TasksUseCase) : ViewModel() {

    private val _tittleOfTask = MutableStateFlow("")
    val tittleOfTask: StateFlow<String> = _tittleOfTask

    private val _descriptionOfTask = MutableStateFlow("")
    val descriptionOfTask: StateFlow<String> = _descriptionOfTask

    private val _dataOfTask = mutableStateOf<LocalDate?>(null)
    val dataOfTask: State<LocalDate?> = _dataOfTask

    private val _timeOfTask = mutableStateOf<LocalTime?>(null)
    val timeOfTask: State<LocalTime?> = _timeOfTask

    private val _isDoneTask = mutableStateOf<Boolean>(false)
    val isDoneTask: State<Boolean> = _isDoneTask

    private val _listOfSubTasks = mutableStateListOf<Subtasks>()
    val listOfSubTasks: List<Subtasks> = _listOfSubTasks

    private val _priority = MutableStateFlow(ImportanceOfTask.MEDIUM)
    val priority: StateFlow<ImportanceOfTask> = _priority


    private val _event = MutableSharedFlow<CreatingTaskUiEvents>()
    val uiEvents = _event

    private fun createTask(task: Task) {
        tasksUseCase.createTask(task).onEach { result ->
            when (result) {
                is Responses.Error<*> -> {

                }

                is Responses.Loading<*> -> {

                }

                is Responses.Success<*> -> {

                }
            }

        }.launchIn(viewModelScope)
    }

    fun onEvent(event: CreatingTaskEvents) {
        when (event) {
            is CreatingTaskEvents.GetTakById -> {

            }

            CreatingTaskEvents.NavigateToBack -> {
                viewModelScope.launch {
                    _event.emit(CreatingTaskUiEvents.NavigateToBack)
                }
            }

            CreatingTaskEvents.SaveTask -> {
                viewModelScope.launch {
                    val task = Task(
                        id = 0,
                        tittle = _tittleOfTask.value,
                        description = _descriptionOfTask.value,
                        date = _dataOfTask.value,
                        time = _timeOfTask.value,
                        listOfSubtasks = _listOfSubTasks as List<Subtasks>,
                        isCompleted = false,
                        importance =_priority.value
                    )
                    createTask(task)
                }
            }

            is CreatingTaskEvents.UpdateDescriptionOfTask -> {
                _descriptionOfTask.value = event.description
            }

            is CreatingTaskEvents.UpdateTittleOfTask -> {
                _tittleOfTask.value = event.tittle
            }

            is CreatingTaskEvents.UpdateDateOfTask -> {
                _dataOfTask.value = event.date
            }

            is CreatingTaskEvents.UpdateIsDoneTask -> {
                _isDoneTask.value = event.isDone
            }

            is CreatingTaskEvents.UpdateTimeOfTask -> {
                _timeOfTask.value = event.time
            }

            CreatingTaskEvents.AddTextFieldForSubTask -> {
                _listOfSubTasks.add(Subtasks(description = "", isCompleted = false))

            }

            is CreatingTaskEvents.UpdateTempSubTaskDescription -> {
                _listOfSubTasks[event.index] = _listOfSubTasks[event.index].copy(description = event.description)

            }
            is CreatingTaskEvents.UpdateTempSubTaskIsDone ->{
                _listOfSubTasks[event.index] = _listOfSubTasks[event.index].copy(isCompleted = event.isDone)

            }

            is CreatingTaskEvents.UpdatePriorityOfTask -> {
                _priority.value = event.priority
            }
        }
    }
}