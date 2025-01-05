package com.example.bookmarks_presentation.view_models

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aopr.firebase_domain.firestore_user_data.FireUser
import com.aopr.shared_domain.Responses
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
class AllBookmarksViewModel(private val bookmarksUseCase: BookmarksUseCase):ViewModel() {

    private val _listOfBookmarks = MutableStateFlow<List<Bookmark>?>(null)
    val listOfBookmarks: StateFlow<List<Bookmark>?> = _listOfBookmarks

    private val _event = MutableSharedFlow<AllBookmarksUiEvents>()
    val event = _event.asSharedFlow()

    init {
        onEvent(AllBookmarksEvents.GetAllBookmarks)
    }

    private fun getAllBookmarks(){
        bookmarksUseCase.getAllBookmarks().onEach { result->
            when(result){
                is Responses.Error<*> -> {

                }
                is Responses.Loading<*> -> {

                }
                is Responses.Success<*> -> {
                    result.data?.collect { it->
                        Log.wtf("Meerka", it.toString())
                        _listOfBookmarks.value = it
                    }
                }
            }


        }.launchIn(viewModelScope)
    }
    private fun deleteBookmark(bookmark: Bookmark){

        bookmarksUseCase.deleteBookmark(bookmark).onEach {result->
            when(result){
                is Responses.Error<*> -> {

                }
                is Responses.Loading<*> -> {

                }
                is Responses.Success<*> -> {

                }
            }

        }.launchIn(viewModelScope)
    }

    fun onEvent(event: AllBookmarksEvents){
        when(event){
            is AllBookmarksEvents.DeleteBookmark -> {
                viewModelScope.launch{
                    deleteBookmark(event.bookmark)
                }
            }
            AllBookmarksEvents.NavigateBack -> {

            }
            is AllBookmarksEvents.NavigateToBookmarkById -> {
                viewModelScope.launch{
                    _event.emit(NavigateToBookmarkById(event.id))
                }
            }

            AllBookmarksEvents.GetAllBookmarks -> {
                viewModelScope.launch {
                    getAllBookmarks()
                }
            }
        }
    }

}