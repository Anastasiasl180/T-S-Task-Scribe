package com.example.bookmarks_presentation.view_models

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aopr.shared_domain.Responses
import com.example.bookmarks_domain.interactors.BookmarksUseCase
import com.example.bookmarks_domain.models.Category
import com.example.bookmarks_presentation.events.main_events.MainEvents
import com.example.bookmarks_presentation.events.main_events.UiMainEvents
import com.example.bookmarks_presentation.events.main_events.UiMainEvents.*
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class MainViewModel(private val bookmarksUseCase: BookmarksUseCase) : ViewModel() {

    private val _isDialogForAddingCategoryIsShowed = mutableStateOf(false)
    val isDialogForAddingCategoryIsShowed: State<Boolean> = _isDialogForAddingCategoryIsShowed

    private val _tittleOfCategory = mutableStateOf("")
    val tittleOfCategory: State<String> = _tittleOfCategory

    private val _listOfCategories = MutableStateFlow<List<Category>>(emptyList())
    val listOfCategories: StateFlow<List<Category>> = _listOfCategories

    private val _event = MutableSharedFlow<UiMainEvents>()
    val event = _event.asSharedFlow()

    init {
        getAllCategories()
    }

    private fun deleteCategory(category: Category) {
        bookmarksUseCase.deleteCategory(category).onEach { result ->
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

    private fun createCategory(category: Category) {
        bookmarksUseCase.createCategory(category).onEach { result ->
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

    private fun getAllCategories() {
        bookmarksUseCase.getAllCategories().onEach { result ->
            when (result) {
                is Responses.Error<*> -> {

                }

                is Responses.Loading<*> -> {

                }

                is Responses.Success<*> -> {
                    result.data?.collect { category ->
                        _listOfCategories.value = category
                    }
                }
            }

        }.launchIn(viewModelScope)
    }

    fun onEvent(event: MainEvents) {
        when (event) {
            is MainEvents.NavigateToCreateBookmark -> {
                viewModelScope.launch {
                    _event.emit(NavigateToCreateBookmark(event.id))
                }
            }

            MainEvents.AddCategory -> {
                viewModelScope.launch {
                    val category = Category(
                        tittle = _tittleOfCategory.value
                    )
                    createCategory(category)
                }
            }

            is MainEvents.DeleteCategory -> {
                viewModelScope.launch {
                    deleteCategory(event.category)
                }
            }

            MainEvents.NavigateBack -> {

            }

            MainEvents.NavigateToAllBookmarks -> {
                viewModelScope.launch {
                    _event.emit(UiMainEvents.NavigateToAllBookMarks)
                }
            }

            MainEvents.HideDialogForAddingCategory -> {
                _isDialogForAddingCategoryIsShowed.value = false
            }

            MainEvents.ShowDialogForAddingCategory -> {
                _isDialogForAddingCategoryIsShowed.value = true
            }

            is MainEvents.UpdateTittleOfCategory -> {
                _tittleOfCategory.value = event.tittle
            }

            is MainEvents.NavigateToBookmarksByCategoryId -> {
                viewModelScope.launch {
                    _event.emit(UiMainEvents.NavigateToBookmarksByCategoryId(event.id))
                }
            }
        }

    }


}