package com.aopr.home.home_screen.view_model

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.aopr.home.home_screen.view_model.events.homeEvents.HomeEvent
import com.aopr.home.home_screen.view_model.events.homeEvents.HomeUiEvents
import com.aopr.notes_domain.interactors.NotesUseCase
import com.aopr.shared_domain.Responses
import com.aopr.shared_domain.interactors.UserDataForFireBase
import com.aopr.shared_ui.util.ViewModelKit
import com.aopr.tasks_domain.interactors.TasksUseCase
import com.aopr.tasks_domain.models.Subtasks
import com.aopr.tasks_domain.models.Task
import com.example.bookmarks_domain.interactors.BookmarksUseCase
import com.example.home_domain.HomeRepository.HomeUseCase.HomeUseCase
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
class HomeViewModel(
    private val notesUseCase: NotesUseCase,
    private val tasksUseCase: TasksUseCase,
    private val bookmarksUseCase: BookmarksUseCase,
    private val homeUseCase: HomeUseCase
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

    private val _userDataForFireBase = MutableStateFlow<UserDataForFireBase?>(null)
    val userDataForFireBase: StateFlow<UserDataForFireBase?> = _userDataForFireBase

    private val _bitMapImage = MutableStateFlow<Bitmap?>(null)
    val bitmapImage: StateFlow<Bitmap?> = _bitMapImage

    private val _userName = MutableStateFlow<String?>(null)
    val userName: StateFlow<String?> = _userName

    private val _listOfSubTasks = mutableStateListOf<Subtasks>()
    val listOfSubTasks: List<Subtasks> = _listOfSubTasks

    private val _listOfTodaysTasks = MutableStateFlow<List<Task>>(emptyList())
    val listOfTodaysTasks: StateFlow<List<Task>> = _listOfTodaysTasks

    private val _event = MutableSharedFlow<HomeUiEvents>()
    val uiEvents = _event

    init {
        viewModelScope.launch {
            getUserDataFromDB()
        }
        viewModelScope.launch {
            filterTasksByDate()
        }

    }


    private fun filterTasksByDate() {
        tasksUseCase.filterTaskByTodayDate().onEach { result ->
            when (result) {
                is Responses.Error -> {

                }

                is Responses.Loading -> {

                }

                is Responses.Success -> {
                    result.data?.collect { tasks ->
                        _listOfTodaysTasks.value = tasks

                    }
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun getUserDataFromDB() {
        homeUseCase.getUserDataFromDB().onEach { result ->
            when (result) {
                is Responses.Error -> {

                }

                is Responses.Loading -> {

                }

                is Responses.Success -> {
                    val userData = result.data
                    _userDataForFireBase.value = result.data
                    _userName.value = result.data?.name
                    _bitMapImage.value = userData?.profileImage?.let { base64ToBitmap(it) }
                }
            }

        }
    }

    private fun deleteAllDataFromRoom() {
        homeUseCase.deleteAllUserData().onEach { result ->
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


    override fun onEvent(event: HomeEvent) {
        when (event) {

            HomeEvent.NavigateToAllNotes -> {
                viewModelScope.launch {
                    _event.emit(HomeUiEvents.NavigateToAllNotesScreen)
                }
            }

            HomeEvent.NavigateToAllTasks -> {
                viewModelScope.launch {
                    _event.emit(HomeUiEvents.NavigateToAllTasks)
                }
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
                    //  _event.emit(HomeUiEvents.NavigateToRegistrationScreen)
                }
            }

            HomeEvent.NavigateToCreateBookmark -> {
                viewModelScope.launch {
                    _event.emit(HomeUiEvents.NavigateToCreateBookmarkScreen)
                }
            }

            HomeEvent.NavigateToCreateNote -> {
                viewModelScope.launch {
                    _event.emit(HomeUiEvents.NavigateToCreateNotesScreen)
                }
            }

            HomeEvent.NavigateToCreateTask -> {
                viewModelScope.launch {
                    _event.emit(HomeUiEvents.NavigateToCreateTaskScreen)
                }
            }

            is HomeEvent.NavigateToCalendarScreen -> {
                viewModelScope.launch {
                    _event.emit(HomeUiEvents.NavigateToCalendarScreen)
                }
            }
        }
    }

    private fun base64ToBitmap(base64String: String): Bitmap {
        val decodedBytes = Base64.decode(base64String, Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
    }

}