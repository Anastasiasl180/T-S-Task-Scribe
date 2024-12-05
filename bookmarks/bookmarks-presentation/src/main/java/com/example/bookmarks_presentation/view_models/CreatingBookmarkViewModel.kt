package com.example.bookmarks_presentation.view_models

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.webkit.MimeTypeMap
import android.widget.Toast
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aopr.shared_domain.Responses
import com.example.bookmarks_domain.interactors.BookmarksUseCase
import com.example.bookmarks_domain.models.Bookmark
import com.example.bookmarks_domain.models.Category
import com.example.bookmarks_presentation.events.creating_bookmark_events.CreatingBookmarkEvents
import com.example.bookmarks_presentation.events.main_events.MainEvents
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class CreatingBookmarkViewModel(private val bookmarksUseCase: BookmarksUseCase) : ViewModel() {

    private val _tittleOfBookmark = MutableStateFlow("")
    val tittle: StateFlow<String> = _tittleOfBookmark

    private val _idOfTask = mutableStateOf<Int?>(null)
    val idOfTask: State<Int?> = _idOfTask

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


    init {
       oEvent(CreatingBookmarkEvents.GetAllCategories)
    }

    private fun createBookmark(bookmark: Bookmark) {
        bookmarksUseCase.createBookmark(bookmark).onEach { result ->
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

    private fun getAllCategories(){
        bookmarksUseCase.getAllCategories().onEach{result->
            when(result){
                is Responses.Error<*> -> {

                }
                is Responses.Loading<*> -> {

                }
                is Responses.Success<*> -> {
                    result.data?.collect(){
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
                        _idOfTask.value = bookmark.id
                        _tittleOfBookmark.value = bookmark.tittle
                        _fileUri.value = bookmark.fileUri
                        _contentUrl.value = bookmark.url
                        _idOfCategory.value = bookmark.categoryId

                    }

                }
            }
        }.launchIn(viewModelScope)
    }


    fun oEvent(events: CreatingBookmarkEvents) {
        when (events) {
            is CreatingBookmarkEvents.GetBookmarkById -> {
                viewModelScope.launch {
                   events.id?.let{it
                       getBookMarkById(it)
                   }
                }
            }

            CreatingBookmarkEvents.SaveBookmark -> {
                viewModelScope.launch {
                    val bookmark = Bookmark(
                        id = _idOfTask.value ?:0,
                        tittle = _tittleOfBookmark.value,
                        fileUri = _fileUri.value,
                        url = _contentUrl.value,
                        categoryId = _idOfCategory.value
                    )
                    createBookmark(bookmark)
                }
            }

            is CreatingBookmarkEvents.UpdateTittleOfBookmark -> {
                _tittleOfBookmark.value = events.tittle
            }

            is CreatingBookmarkEvents.AddFile -> {
                _fileUri.value = events.uri
            }

            is CreatingBookmarkEvents.AddLink -> {
                _contentUrl.value = events.url
            }

            CreatingBookmarkEvents.CancelFile -> {
                _fileUri.value = null
            }

            CreatingBookmarkEvents.CancelLink -> {
                _contentUrl.value = null
            }

            is CreatingBookmarkEvents.OpenFile -> TODO()
            is CreatingBookmarkEvents.OpenLink -> TODO()

            is CreatingBookmarkEvents.GetNewBookmarkWithCategoryId -> {
                _idOfCategory.value = events.id
                }

            CreatingBookmarkEvents.ExpandDropDownMenu -> {
                _isDropDownMenuExpanded.value = !_isDropDownMenuExpanded.value
            }

            CreatingBookmarkEvents.GetAllCategories -> {
                viewModelScope.launch{
                    getAllCategories()
                }
            }

            is CreatingBookmarkEvents.SelectCategory -> {
                _idOfCategory.value = events.id
            }
        }
    }


}
