package com.aopr.tasks_presentation.viewModels

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.aopr.shared_domain.Responses
import com.aopr.shared_ui.util.ViewModelKit
import com.aopr.tasks_domain.interactors.TasksUseCase
import com.aopr.tasks_domain.models.ImportanceOfTask
import com.aopr.tasks_domain.models.Subtasks
import com.aopr.tasks_domain.models.Task
import com.aopr.tasks_presentation.events.creating_task_events.CreatingTaskEvents
import com.aopr.tasks_presentation.events.creating_task_events.CreatingTaskUiEvents
import kotlinx.coroutines.delay
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
class CreatingTaskViewModel(private val tasksUseCase: TasksUseCase) :
    ViewModelKit<CreatingTaskEvents, CreatingTaskUiEvents>() {

    private val _tittleOfTask = MutableStateFlow("")
    val tittleOfTask: StateFlow<String> = _tittleOfTask

    private val _idOfTask = mutableStateOf<Int?>(null)
    val idOfTask: State<Int?> = _idOfTask

    private val _descriptionOfTask = MutableStateFlow("")
    val descriptionOfTask: StateFlow<String> = _descriptionOfTask

    private val _dateOfTaskForReminder = mutableStateOf<LocalDate?>(null)
    val dataOfTaskForReminder: State<LocalDate?> = _dateOfTaskForReminder

    private val _dateOfTaskToBeDone = mutableStateOf<LocalDate?>(null)
    val dataOfTaskToBeDone: State<LocalDate?> = _dateOfTaskToBeDone

    private val _timeOfTask = mutableStateOf<LocalTime?>(null)
    val timeOfTask: State<LocalTime?> = _timeOfTask

    private val _isDoneTask = mutableStateOf(false)
    val isDoneTask: State<Boolean> = _isDoneTask

    private val _listOfSubTasks = mutableStateListOf<Subtasks>()
    val listOfSubTasks: List<Subtasks> = _listOfSubTasks

    private val _priority = MutableStateFlow(ImportanceOfTask.MEDIUM)
    val priority: StateFlow<ImportanceOfTask> = _priority

    private val _isCalendarVisible = MutableStateFlow(false)
    val isCalendarVisible: StateFlow<Boolean> = _isCalendarVisible

    private val _isClockVisible = MutableStateFlow(false)
    val isClockVisible: StateFlow<Boolean> = _isClockVisible

    private val _datesWithTasks = mutableStateListOf<LocalDate?>()
    val datesWithTasks: List<LocalDate?> = _datesWithTasks

    private val _tasksByDate = mutableStateListOf<Task?>(null)
    val tasksByDate: List<Task?> = _tasksByDate


    private val _event = MutableSharedFlow<CreatingTaskUiEvents>()
    val uiEvents = _event
    private val _calendarMode = mutableStateOf(CalendarMode.TASK_DONE)
    val calendarMode: State<CalendarMode> = _calendarMode

    enum class CalendarMode {
        TASK_DONE, REMINDER
    }

    init {
        onEvent(CreatingTaskEvents.LoadDatesWithTask)
    }
    fun addSubTaskField() {
        _listOfSubTasks.add(Subtasks(description = "", isCompleted = false))
    }

    fun updateSubTask(index: Int, description: String, isCompleted: Boolean) {
        if (index in _listOfSubTasks.indices) {
            _listOfSubTasks[index] = _listOfSubTasks[index].copy(description = description, isCompleted = isCompleted)
        }
    }

    fun removeSubTask(index: Int) {
        if (index in _listOfSubTasks.indices) {
            _listOfSubTasks.removeAt(index)
        }
    }

    fun validateAndSaveTask(): Boolean {
        val nonEmptySubTasks = _listOfSubTasks.filter { it.description.isNotBlank() }
        if (nonEmptySubTasks.isEmpty()) {
            // Notify user that at least one valid subtask is required
                return false
        }
        _listOfSubTasks.clear()
        _listOfSubTasks.addAll(nonEmptySubTasks)
        return true
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
                    result.data?.collect() {
                        _tasksByDate.clear()
                        _tasksByDate.addAll(it)
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
                    result.data?.collect() {
                        val dates = it.mapNotNull { it.dateOfTaskToBeDone }
                        _datesWithTasks.addAll(dates)
                    }
                }
            }

        }.launchIn(viewModelScope)
    }

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

    private fun getTaskById(id: Int) {
        tasksUseCase.getTaskById(id).onEach { result ->
            when (result) {
                is Responses.Error -> {

                }

                is Responses.Loading -> {

                }

                is Responses.Success -> {
                    result.data?.collect() { task ->
                        _idOfTask.value = task.id
                        _priority.value = task.importance
                        _dateOfTaskForReminder.value = task.dateForReminder
                        _timeOfTask.value = task.timeForReminder
                        _isDoneTask.value = task.isCompleted
                        task.listOfSubtasks?.let { _listOfSubTasks.addAll(it) }
                        _descriptionOfTask.value = task.description
                        _tittleOfTask.value = task.tittle
                        _dateOfTaskToBeDone.value = task.dateOfTaskToBeDone
                    }
                }
            }

        }.launchIn(viewModelScope)
    }

    override fun onEvent(event: CreatingTaskEvents) {
        when (event) {
            is CreatingTaskEvents.GetTakById -> {
                viewModelScope.launch {
                    event.id?.let { getTaskById(it) }
                }
            }

            CreatingTaskEvents.NavigateToBack -> {
                viewModelScope.launch {
                    _event.emit(CreatingTaskUiEvents.NavigateToBack)
                }
            }

            CreatingTaskEvents.SaveTask -> {
                viewModelScope.launch {
                    val task = Task(
                        id = _idOfTask.value ?: 0,
                        tittle = _tittleOfTask.value,
                        description = _descriptionOfTask.value,
                        dateForReminder = _dateOfTaskForReminder.value,
                        dateOfTaskToBeDone = _dateOfTaskToBeDone.value,
                        timeForReminder = _timeOfTask.value,
                        listOfSubtasks = _listOfSubTasks as List<Subtasks>,
                        isCompleted = false,
                        importance = _priority.value
                    )
                    createTask(task)
                    delay(500)
                    onEvent(CreatingTaskEvents.NavigateToBack)
                }
            }

            is CreatingTaskEvents.UpdateDescriptionOfTask -> {
                _descriptionOfTask.value = event.description
            }

            is CreatingTaskEvents.UpdateTittleOfTask -> {
                _tittleOfTask.value = event.tittle
            }

            is CreatingTaskEvents.UpdateDateOfTaskForReminder -> {
                if (_calendarMode.value == CalendarMode.REMINDER) {
                    _dateOfTaskForReminder.value = event.date
                }
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
                _listOfSubTasks[event.index] =
                    _listOfSubTasks[event.index].copy(description = event.description)

            }

            is CreatingTaskEvents.UpdateTempSubTaskIsDone -> {
                _listOfSubTasks[event.index] =
                    _listOfSubTasks[event.index].copy(isCompleted = event.isDone)

            }

            is CreatingTaskEvents.UpdatePriorityOfTask -> {
                _priority.value = event.priority
            }

            CreatingTaskEvents.HideCalendar -> {
                _isCalendarVisible.value = false
            }

            CreatingTaskEvents.ShowCalendarForToBeDone -> {
                _calendarMode.value = CalendarMode.TASK_DONE
                _isCalendarVisible.value = true
            }

            CreatingTaskEvents.HideClock -> {
                _isClockVisible.value = false
            }

            CreatingTaskEvents.ShowClock -> {
                _isClockVisible.value = true
            }

            CreatingTaskEvents.LoadDatesWithTask -> {
                viewModelScope.launch {
                    getDatesWithTasks()
                }
            }

            is CreatingTaskEvents.GetTasksByDate -> {
                viewModelScope.launch {
                    getTasksByDate(event.date)
                }
            }

            is CreatingTaskEvents.UpdateDateOfTaskToBeDone -> {
                if (_calendarMode.value == CalendarMode.TASK_DONE) {
                    _dateOfTaskToBeDone.value = event.date
                }
            }

            CreatingTaskEvents.ShowCalendarForReminder -> {
                _calendarMode.value = CalendarMode.REMINDER
                _isCalendarVisible.value = true
            }

        }
    }
}
