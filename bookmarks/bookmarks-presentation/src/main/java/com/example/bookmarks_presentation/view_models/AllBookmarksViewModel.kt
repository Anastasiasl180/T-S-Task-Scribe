package com.example.bookmarks_presentation.view_models

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aopr.shared_domain.Responses
import com.example.bookmarks_domain.interactors.BookmarksUseCase
import com.example.bookmarks_domain.models.Bookmark
import com.example.bookmarks_presentation.events.all_bookmarks_event.AllBookmarksUiEvents
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class AllBookmarksViewModel(private val bookmarksUseCase: BookmarksUseCase):ViewModel() {

    private val _listOfBookmarks = mutableStateOf<List<Bookmark>?>(null)
    val listOfBookmarks:State<List<Bookmark>?> = _listOfBookmarks

    private val _event = MutableSharedFlow<AllBookmarksUiEvents>()
    val event = _event.asSharedFlow()

    init {
        getAllBookmarks()
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
                        _listOfBookmarks.value = it
                    }
                }
            }


        }.launchIn(viewModelScope)
    }

}