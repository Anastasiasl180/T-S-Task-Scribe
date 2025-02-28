package com.example.bookmarks_presentation.view_models

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aopr.shared_domain.Responses
import com.example.bookmarks_domain.interactors.BookmarksUseCase
import com.example.bookmarks_domain.models.Category
import com.example.bookmarks_presentation.view_models.events.categories_events.CategoriesEvents
import com.example.bookmarks_presentation.view_models.events.categories_events.UiCategoriesEvents
import com.example.bookmarks_presentation.view_models.events.categories_events.UiCategoriesEvents.NavigateToAllBookMarks
import com.example.bookmarks_presentation.view_models.events.categories_events.UiCategoriesEvents.NavigateToBookmarksByCategoryId
import com.example.bookmarks_presentation.view_models.events.categories_events.UiCategoriesEvents.NavigateToCreateBookmark
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class CategoriesViewModel(private val bookmarksUseCase: BookmarksUseCase) : ViewModel() {

    private val _isDialogForAddingCategoryIsShowed = mutableStateOf(false)
    val isDialogForAddingCategoryIsShowed: State<Boolean> = _isDialogForAddingCategoryIsShowed

    private val _isInSelectionMode = MutableStateFlow(false)
    val isInSelectedMode: StateFlow<Boolean> = _isInSelectionMode

    private val _tittleOfCategory = mutableStateOf("")
    val tittleOfCategory: State<String> = _tittleOfCategory

    private val _listOfCategories = MutableStateFlow<List<Category>>(emptyList())
    val listOfCategories: StateFlow<List<Category>> = _listOfCategories

    private val _listOfCategoriesToDelete = mutableStateListOf<Category>()
    val listOfCategoriesToDelete: List<Category> = _listOfCategoriesToDelete

    private val _event = MutableSharedFlow<UiCategoriesEvents>()
    val event = _event.asSharedFlow()

    init {
        getAllCategories()
    }


    private fun deleteSeveralCategories(category: List<Category>) {
        bookmarksUseCase.deleteSeveralCategories(category).onEach { result ->
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
                    onEvent(CategoriesEvents.HideDialogForAddingCategory)
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

    fun onEvent(event: CategoriesEvents) {
        when (event) {
            is CategoriesEvents.NavigateToCreateBookmark -> {
                viewModelScope.launch {
                    _event.emit(NavigateToCreateBookmark(event.id))
                }
            }

            CategoriesEvents.AddCategory -> {
                viewModelScope.launch {
                    val category = Category(
                        tittle = _tittleOfCategory.value
                    )
                    createCategory(category)
                }
            }

            is CategoriesEvents.DeleteCategory -> {
                viewModelScope.launch {
                    deleteCategory(event.category)
                }
            }

            CategoriesEvents.NavigateBack -> {
                viewModelScope.launch {
                    _event.emit(UiCategoriesEvents.NavigateBack)
                }
            }

            CategoriesEvents.NavigateToAllBookmarks -> {
                viewModelScope.launch {
                    _event.emit(NavigateToAllBookMarks)
                }
            }

            CategoriesEvents.HideDialogForAddingCategory -> {
                _isDialogForAddingCategoryIsShowed.value = false
            }

            CategoriesEvents.ShowDialogForAddingCategory -> {
                _isDialogForAddingCategoryIsShowed.value = true
            }

            is CategoriesEvents.UpdateTittleOfCategory -> {
                _tittleOfCategory.value = event.tittle
            }

            is CategoriesEvents.NavigateToBookmarksByCategoryId -> {
                viewModelScope.launch {
                    _event.emit(NavigateToBookmarksByCategoryId(event.id))
                }
            }

            CategoriesEvents.TurnOnSelectionModeForDelete -> {
                if (listOfCategories.value.isNotEmpty()) {
                    _isInSelectionMode.value = !_isInSelectionMode.value
                }
            }

            is CategoriesEvents.AddCategoryForDeletion -> {
                _listOfCategoriesToDelete.add(event.category)
            }

            CategoriesEvents.DeleteSeveralCategories -> {
                if (_listOfCategoriesToDelete.isNotEmpty()) {
                    val list = _listOfCategoriesToDelete.toList()
                    deleteSeveralCategories(list)
                    _listOfCategoriesToDelete.clear()
                    _isInSelectionMode.value = false
                }
            }

            is CategoriesEvents.RemoveCategoryForDeletion -> {
                _listOfCategoriesToDelete.remove(event.category)
            }
        }

    }


}