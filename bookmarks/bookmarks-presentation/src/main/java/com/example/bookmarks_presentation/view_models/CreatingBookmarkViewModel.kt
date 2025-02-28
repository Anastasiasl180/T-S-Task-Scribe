package com.example.bookmarks_presentation.view_models

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.aopr.firebase_domain.firestore_user_data.FireUser
import com.aopr.shared_domain.Responses
import com.aopr.shared_ui.util.ViewModelKit
import com.example.bookmarks_domain.interactors.BookmarksUseCase
import com.example.bookmarks_domain.models.Bookmark
import com.example.bookmarks_domain.models.Category
import com.example.bookmarks_presentation.view_models.events.creating_bookmark_events.CreatingBookmarkEvents
import com.example.bookmarks_presentation.view_models.events.creating_bookmark_events.CreatingBookmarkUiEvents
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class CreatingBookmarkViewModel(
    private val bookmarksUseCase: BookmarksUseCase,
    private val fireUser: FireUser
) : ViewModelKit<CreatingBookmarkEvents, CreatingBookmarkUiEvents>() {

    private val _tittleOfBookmark = MutableStateFlow("")
    val tittle: StateFlow<String> = _tittleOfBookmark

    private val _idOfBookamrk = mutableStateOf<Int?>(null)
    val idOfBookmark: State<Int?> = _idOfBookamrk

    private val _idOfCategory = MutableStateFlow<Int?>(null)
    val idOfCategory: StateFlow<Int?> = _idOfCategory

    private val _contentUrl = MutableStateFlow<String?>(null)
    val contentUrl: StateFlow<String?> = _contentUrl

    private val _isDropDownMenuExpanded = mutableStateOf(false)
    val isDropDownMenuExpanded: State<Boolean> = _isDropDownMenuExpanded

    private val _listOfCategories = MutableStateFlow<List<Category>>(emptyList())
    val listOfCategories: StateFlow<List<Category>> = _listOfCategories

    private val _fileUri = MutableStateFlow<String?>(null)
    val fileUri: StateFlow<String?> = _fileUri

    private val _event = MutableSharedFlow<CreatingBookmarkUiEvents>()
    val event = _event.asSharedFlow()


    init {
        onEvent(CreatingBookmarkEvents.GetAllCategories)
    }

    private fun createBookmark(bookmark: Bookmark, userId: String?) {
        bookmarksUseCase.createBookmark(bookmark, userId).onEach { result ->
            when (result) {
                is Responses.Error -> {
                    hideInfoBar()
                    result.message?.let { showShortInfoBar(it, 2) }

                }

                is Responses.Loading -> {

                }

                is Responses.Success -> {
                    onEvent(CreatingBookmarkEvents.NavigateBack)
                }
            }

        }.launchIn(viewModelScope)
    }

    private fun getAllCategories() {
        bookmarksUseCase.getAllCategories().onEach { result ->
            when (result) {
                is Responses.Error<*> -> {

                }

                is Responses.Loading<*> -> {

                }

                is Responses.Success<*> -> {
                    result.data?.collect() {
                        _listOfCategories.value = it
                    }
                }
            }

        }.launchIn(viewModelScope)
    }

    private fun getBookMarkById(id: Int) {
        bookmarksUseCase.getBookmarkById(id).onEach { result ->
            when (result) {
                is Responses.Error<*> -> {

                }

                is Responses.Loading<*> -> {
                }

                is Responses.Success<*> -> {
                    result.data?.collect() { bookmark ->
                        _idOfBookamrk.value = bookmark.id
                        _tittleOfBookmark.value = bookmark.tittle
                        _fileUri.value = bookmark.fileUri
                        _contentUrl.value = bookmark.url
                        _idOfCategory.value = bookmark.categoryId
                    }

                }
            }
        }.launchIn(viewModelScope)
    }


    override fun onEvent(event: CreatingBookmarkEvents) {
        when (event) {
            is CreatingBookmarkEvents.GetBookmarkById -> {
                viewModelScope.launch {

                    event.bookmarkInfo.bookmarkId?.let {
                        getBookMarkById(it)
                    }
                    _idOfCategory.value = event.bookmarkInfo.categoryId
                }
            }

            is CreatingBookmarkEvents.SaveBookmark -> {
                viewModelScope.launch {
                    val bookmark = Bookmark(
                        id = _idOfBookamrk.value ?: 0,
                        tittle = _tittleOfBookmark.value,
                        fileUri = _fileUri.value,
                        url = _contentUrl.value,
                        categoryId = _idOfCategory.value
                    )
                    createBookmark(bookmark, fireUser.userId)
                }
            }

            is CreatingBookmarkEvents.UpdateTittleOfBookmark -> {
                _tittleOfBookmark.value = event.tittle
            }

            is CreatingBookmarkEvents.AddFile -> {
                _fileUri.value = event.uri
            }

            is CreatingBookmarkEvents.AddLink -> {
                _contentUrl.value = event.url
            }

            CreatingBookmarkEvents.CancelFile -> {
                _fileUri.value = null
            }

            CreatingBookmarkEvents.CancelLink -> {
                _contentUrl.value = null
            }

         CreatingBookmarkEvents.ExpandDropDownMenu -> {
                _isDropDownMenuExpanded.value = !_isDropDownMenuExpanded.value
            }

            CreatingBookmarkEvents.GetAllCategories -> {
                viewModelScope.launch {
                    getAllCategories()
                }
            }

            is CreatingBookmarkEvents.SelectCategory -> {
                _idOfCategory.value = event.id
            }

            CreatingBookmarkEvents.NavigateBack -> {
                viewModelScope.launch {
                    _event.emit(CreatingBookmarkUiEvents.NavigateBack)
                }
            }
        }
    }


}
