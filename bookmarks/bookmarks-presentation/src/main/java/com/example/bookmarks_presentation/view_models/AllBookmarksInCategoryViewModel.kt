package com.example.bookmarks_presentation.view_models

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aopr.shared_domain.Responses
import com.example.bookmarks_domain.interactors.BookmarksUseCase
import com.example.bookmarks_domain.models.Bookmark
import com.example.bookmarks_presentation.view_models.events.all_bookmarks_in_category_event.AllBookmarksInCategoryEvents
import com.example.bookmarks_presentation.view_models.events.all_bookmarks_in_category_event.AllBookmarksInCategoryUiEvents
import com.example.bookmarks_presentation.view_models.events.all_bookmarks_in_category_event.AllBookmarksInCategoryUiEvents.NavigateBack
import com.example.bookmarks_presentation.view_models.events.all_bookmarks_in_category_event.AllBookmarksInCategoryUiEvents.NavigateToBookmarkById
import com.example.bookmarks_presentation.view_models.events.all_bookmarks_in_category_event.AllBookmarksInCategoryUiEvents.NavigateToCreateBookmarkWithCategoryId
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class AllBookmarksInCategoryViewModel(private val bookmarksUseCase: BookmarksUseCase) :
    ViewModel() {

    private val _listOfBookmarks = MutableStateFlow<List<Bookmark>?>(emptyList())
    val listOfBookmarks: StateFlow<List<Bookmark>?> = _listOfBookmarks

    private val _categoryId = MutableStateFlow<Int>(0)
    val categoryId: StateFlow<Int> = _categoryId

    private val _categoryTittle = mutableStateOf<String?>(null)
    val categoryTittle: State<String?> = _categoryTittle

    private val _isInSelectionMode = MutableStateFlow(false)
    val isInSelectedMode: StateFlow<Boolean> = _isInSelectionMode

    private val _listOfBookmarksToDelete = mutableStateListOf<Bookmark>()
    val listOfBookmarksToDelete: List<Bookmark> = _listOfBookmarksToDelete

    private val _event = MutableSharedFlow<AllBookmarksInCategoryUiEvents>()
    val event = _event.asSharedFlow()


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

    private fun getCategoryById(id: Int) {
        bookmarksUseCase.getCategoryById(id).onEach { result ->
            when (result) {
                is Responses.Error -> {

                }

                is Responses.Loading -> {

                }

                is Responses.Success -> {
                    result.data?.collect {
                        _categoryTittle.value = it.tittle
                    }
                }
            }

        }.launchIn(viewModelScope)
    }

    private fun getBookmarksByCategoryId(id: Int?) {
        bookmarksUseCase.getBookmarksByCategoryId(id).onEach { result ->
            when (result) {
                is Responses.Error<*> -> {

                }

                is Responses.Loading<*> -> {

                }

                is Responses.Success<*> -> {
                    result.data?.collect {
                        _listOfBookmarks.value = it
                        it.first().categoryId?.let { it1 -> getCategoryById(it1) }
                    }

                    //  _listOfBookmarks.value = result.data ?:emptyList()

                }
            }

        }.launchIn(viewModelScope)

    }

    fun onEvent(event: AllBookmarksInCategoryEvents) {
        when (event) {
            is AllBookmarksInCategoryEvents.GetAllBookmarksByCategoryId -> {
                viewModelScope.launch {
                    _categoryId.value = event.id ?: 0
                    getBookmarksByCategoryId(event.id)
                }
            }

            is AllBookmarksInCategoryEvents.NavigateToCreateBookmarkWithCategoryId -> {
                viewModelScope.launch {
                    _event.emit(NavigateToCreateBookmarkWithCategoryId(_categoryId.value))
                }
            }

            is AllBookmarksInCategoryEvents.DeleteBookmark -> {
                viewModelScope.launch {
                    deleteBookmark(event.bookmark)
                }
            }

            is AllBookmarksInCategoryEvents.DeleteSeveralBookmarks -> {
                if (_listOfBookmarksToDelete.isNotEmpty()) {
                    val list = _listOfBookmarksToDelete.toList()
                    deleteSeveralBookmarks(list)
                    _listOfBookmarksToDelete.clear()
                    _isInSelectionMode.value = false
                }

            }

            AllBookmarksInCategoryEvents.TurnOnSelectionModeForDelete -> {
                if (listOfBookmarks.value != null) {
                    _isInSelectionMode.value = !isInSelectedMode.value
                }
            }

            AllBookmarksInCategoryEvents.NavigateBack -> {
                viewModelScope.launch {
                    _event.emit(NavigateBack)
                }
            }

            is AllBookmarksInCategoryEvents.NavigateToBookmarkById -> {
                viewModelScope.launch {
                    _event.emit(NavigateToBookmarkById(event.id))
                }
            }

            is AllBookmarksInCategoryEvents.RemoveBookmarkForDeletion -> {
                _listOfBookmarksToDelete.remove(event.bookmark)
            }

            is AllBookmarksInCategoryEvents.AddBookmarkForDeletion -> {
                _listOfBookmarksToDelete.add(event.bookmark)
            }
        }
    }
}