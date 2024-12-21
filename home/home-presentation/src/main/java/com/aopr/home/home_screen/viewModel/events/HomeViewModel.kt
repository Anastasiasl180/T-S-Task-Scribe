package com.aopr.home.home_screen.viewModel.events

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.aopr.home.home_screen.viewModel.events.homeEvents.HomeEvent
import com.aopr.home.home_screen.viewModel.events.homeEvents.HomeUiEvents
import com.aopr.notes_domain.interactors.NotesUseCase
import com.aopr.notes_domain.models.Note
import com.aopr.shared_domain.Responses
import com.aopr.shared_ui.util.ViewModelKit
import com.aopr.tasks_domain.interactors.TasksUseCase
import com.aopr.tasks_domain.models.ImportanceOfTask
import com.aopr.tasks_domain.models.Subtasks
import com.aopr.tasks_domain.models.Task
import com.example.bookmarks_domain.interactors.BookmarksUseCase
import com.example.home_domain.HomeRepository.HomeUseCase.HommeUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel
import java.time.LocalDate
import java.time.LocalTime

@KoinViewModel
class HomeViewModel(
    private val notesUseCase: NotesUseCase,
    private val tasksUseCase: TasksUseCase,
    private val bookmarksUseCase: BookmarksUseCase,
    private val hommeUseCase: HommeUseCase
) :
    ViewModelKit<HomeEvent, HomeUiEvents>() {

    private val _tittleOfNote = mutableStateOf("")
    val tittleOfNote: State<String> = _tittleOfNote

    private val _descriptionOfNote = mutableStateOf("")
    val descriptionOfNote: State<String> = _descriptionOfNote

    private val _tittleOfTask = mutableStateOf("")
    val tittleOfTask: State<String> = _tittleOfTask

    private val _descriptionOfTask = mutableStateOf("")
    val descriptionOfTask: State<String> = _descriptionOfTask

    private val _dataOfTaskToBeDone = mutableStateOf<LocalDate>(LocalDate.now())
    val dataOfTaskToBeDone: State<LocalDate> = _dataOfTaskToBeDone

    private val _dataOfTaskForReminder = mutableStateOf<LocalDate?>(null)
    val dataOfTaskForReminder: State<LocalDate?> = _dataOfTaskForReminder

    private val _timeOfTask = mutableStateOf<LocalTime?>(null)
    val timeOfTask: State<LocalTime?> = _timeOfTask

    private val _listOfSubTasks = mutableStateListOf<Subtasks>()
    val listOfSubTasks: List<Subtasks> = _listOfSubTasks

    private val _event = MutableSharedFlow<HomeUiEvents>()
    val uiEvents = _event

    private fun deleteAllDataFromRoom() {
        hommeUseCase.deleteAllDataFromRoom().onEach { result ->
            when (result) {
                is Responses.Error -> {

                }

                is Responses.Loading -> {

                }

                is Responses.Success -> {

                }
            }

        }.launchIn(viewModelScope)
    }


    private fun createNote(note: Note) {
        notesUseCase.createNote(note).onEach { result ->
            when (result) {
                is Responses.Error -> {
                    hideInfoBar()
                    result.message?.let { showShortInfoBar(it, 2) }
                }

                is Responses.Loading -> {

                }

                is Responses.Success -> {
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

    override fun onEvent(event: HomeEvent) {
        when (event) {

            HomeEvent.NavigateToAllNotes -> {
                viewModelScope.launch {
                    _event.emit(HomeUiEvents.NavigateToAllNotesScreen)
                }
            }

            is HomeEvent.SaveNote -> {
                viewModelScope.launch {
                    val note = Note(
                        tittle = _tittleOfNote.value,
                        description = _descriptionOfNote.value,
                        id = 0
                    )
                    createNote(note)
                }
            }

            is HomeEvent.UpdateDescriptionOfNote -> {
                _descriptionOfNote.value = event.description
            }

            is HomeEvent.UpdateTittleOfNote -> {
                _tittleOfNote.value = event.tittle
            }

            HomeEvent.NavigateToAllTasks -> {
                viewModelScope.launch {
                    _event.emit(HomeUiEvents.NavigateToAllTasks)
                }
            }

            HomeEvent.SaveTask -> {
                viewModelScope.launch {
                    val task = Task(
                        id = 0,
                        tittle = _tittleOfTask.value,
                        description = _descriptionOfTask.value,
                        dateForReminder = _dataOfTaskForReminder.value,
                        timeForReminder = _timeOfTask.value,
                        listOfSubtasks = _listOfSubTasks as List<Subtasks>,
                        isCompleted = false,
                        importance = ImportanceOfTask.MEDIUM,
                        dateOfTaskToBeDone = _dataOfTaskToBeDone.value,

                        )
                    createTask(task)
                }
            }

            is HomeEvent.UpdateDescriptionOfTask -> {
                _descriptionOfTask.value = event.description
            }

            is HomeEvent.UpdateTittleOFTask -> {
                _tittleOfTask.value = event.tittle
            }

            HomeEvent.NavigateToAllCategoriesOfBookmarks -> {
                viewModelScope.launch {
                    _event.emit(HomeUiEvents.NavigateToAllCategoriesOfBookmarks)
                }
            }

            HomeEvent.NavigateToThemesByDrawer -> {
                viewModelScope.launch {
                    _event.emit(HomeUiEvents.NavigateToThemesByDrawer)
                }
            }

            HomeEvent.LogOut -> {
                viewModelScope.launch {
                    deleteAllDataFromRoom()
                    _event.emit(HomeUiEvents.NavigateToRegistrationScreen)
                }
            }
        }
    }
}