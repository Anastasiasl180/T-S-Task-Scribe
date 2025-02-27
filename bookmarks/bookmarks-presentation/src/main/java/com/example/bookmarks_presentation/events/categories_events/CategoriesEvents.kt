package com.example.bookmarks_presentation.events.categories_events

import com.example.bookmarks_domain.models.Category

sealed interface CategoriesEvents {
    data object AddCategory:CategoriesEvents
    data object NavigateBack:CategoriesEvents
    data object NavigateToAllBookmarks:CategoriesEvents
    data object ShowDialogForAddingCategory:CategoriesEvents
    data object HideDialogForAddingCategory:CategoriesEvents
    data object TurnOnSelectionModeForDelete : CategoriesEvents
    data class NavigateToCreateBookmark(val id:Int?):CategoriesEvents
    data class DeleteCategory(val category: Category):CategoriesEvents
    data class UpdateTittleOfCategory(val tittle:String):CategoriesEvents
    data class NavigateToBookmarksByCategoryId(val id:Int): CategoriesEvents
    data class DeleteCategories(val categories: List<Category>) : CategoriesEvents
}