package com.example.bookmarks_presentation.view_models

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aopr.shared_domain.Responses
import com.example.bookmarks_domain.interactors.BookmarksUseCase
import com.example.bookmarks_domain.models.Bookmark
import com.example.bookmarks_presentation.events.all_bookmarks_in_category_event.AllBookmarksInCategoryEvents
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class AllBookmarksInCategoryViewModel(private val bookmarksUseCase: BookmarksUseCase) :
    ViewModel() {

    private val _listOfBookmarks = MutableStateFlow<List<Bookmark>>(emptyList())
    val listOfBookmarks: StateFlow<List<Bookmark>> = _listOfBookmarks


    private fun getBookmarksByCategoryId(id: Int?) {
        bookmarksUseCase.getBookmarksByCategoryId(id).onEach { result ->
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
    fun onEvent(event: AllBookmarksInCategoryEvents){
        when(event){
            is AllBookmarksInCategoryEvents.GetAllBookmarksByCategoryId -> {
                viewModelScope.launch{
                        getBookmarksByCategoryId(event.id)
                }
            }
        }
    }
}