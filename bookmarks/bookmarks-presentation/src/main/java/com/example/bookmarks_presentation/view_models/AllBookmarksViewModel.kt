package com.example.bookmarks_presentation.view_models

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.viewModelScope
import com.aopr.shared_domain.Responses
import com.aopr.shared_ui.util.ViewModelKit
import com.example.bookmarks_domain.interactors.BookmarksUseCase
import com.example.bookmarks_domain.models.Bookmark
import com.example.bookmarks_presentation.events.all_bookmarks_event.AllBookmarksEvents
import com.example.bookmarks_presentation.events.all_bookmarks_event.AllBookmarksUiEvents
import com.example.bookmarks_presentation.events.all_bookmarks_event.AllBookmarksUiEvents.*
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class AllBookmarksViewModel(private val bookmarksUseCase: BookmarksUseCase) :
    ViewModelKit<AllBookmarksEvents, AllBookmarksUiEvents>() {

    private val _listOfBookmarks = MutableStateFlow<List<Bookmark>?>(null)
    val listOfBookmarks: StateFlow<List<Bookmark>?> = _listOfBookmarks

    private val _isInSelectionMode = MutableStateFlow(false)
    val isInSelectedMode: StateFlow<Boolean> = _isInSelectionMode

    private val _listOfBookmarksToDelete = mutableStateListOf<Bookmark>()
    val listOfBookmarksToDelete: List<Bookmark> = _listOfBookmarksToDelete

    private val _event = MutableSharedFlow<AllBookmarksUiEvents>()
    val event = _event.asSharedFlow()

    init {
        onEvent(AllBookmarksEvents.GetAllBookmarks)
    }


    fun addBookmarkToDelete(bookmark: Bookmark) {
        _listOfBookmarksToDelete.add(bookmark)
    }

    fun removeBookmarkFromDeletion(bookmark: Bookmark) {
        _listOfBookmarksToDelete.remove(bookmark)
    }

    fun deleteChosenCategories() {
        if (_listOfBookmarksToDelete.isNotEmpty()) {
            val list = _listOfBookmarksToDelete.toList()
            onEvent(AllBookmarksEvents.DeleteSeveralBookmarks(list))
            _listOfBookmarksToDelete.clear()
            _isInSelectionMode.value = false
        }
    }

    private fun deleteSeveralBookmarks(bookmarks: List<Bookmark>) {
        bookmarksUseCase.deleteSeveralBookmarks(bookmarks).onEach { result ->
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

    private fun getAllBookmarks() {
        bookmarksUseCase.getAllBookmarks().onEach { result ->
            when (result) {
                is Responses.Error<*> -> {

                }

                is Responses.Loading<*> -> {

                }

                is Responses.Success<*> -> {
                    result.data?.collect { it ->
                        _listOfBookmarks.value = it
                    }
                }
            }


        }.launchIn(viewModelScope)
    }

    private fun deleteBookmark(bookmark: Bookmark) {

        bookmarksUseCase.deleteBookmark(bookmark).onEach { result ->
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

    override fun onEvent(event: AllBookmarksEvents) {
        when (event) {
            is AllBookmarksEvents.DeleteBookmark -> {
                viewModelScope.launch {
                    deleteBookmark(event.bookmark)
                }
            }

            AllBookmarksEvents.NavigateBack -> {
                viewModelScope.launch {
                    _event.emit(NavigateBack)
                }
            }

            is AllBookmarksEvents.NavigateToBookmarkById -> {
                viewModelScope.launch {
                    _event.emit(NavigateToBookmarkById(event.id))
                }
            }

            AllBookmarksEvents.GetAllBookmarks -> {
                viewModelScope.launch {
                    getAllBookmarks()
                }
            }

            is AllBookmarksEvents.DeleteSeveralBookmarks -> {
                viewModelScope.launch {
                    deleteSeveralBookmarks(event.bookmarks)
                }
            }

            AllBookmarksEvents.TurnOnSelectionModeForDelete -> {
                if (listOfBookmarks.value != null) {
                    _isInSelectionMode.value = !isInSelectedMode.value
                }
            }

            AllBookmarksEvents.NavigateToCreateBookmarkScreen -> {
                viewModelScope.launch {
                    _event.emit(AllBookmarksUiEvents.NavigateToCreateBookmarkScreen)
                }
            }
        }
    }

}