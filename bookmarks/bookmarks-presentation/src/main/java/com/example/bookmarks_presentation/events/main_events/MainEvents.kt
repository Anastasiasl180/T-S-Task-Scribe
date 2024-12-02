package com.example.bookmarks_presentation.events.main_events

import com.example.bookmarks_domain.models.Category

sealed interface MainEvents {
    data class NavigateToCreateBookmark(val id:Int?):MainEvents
    data object AddCategory:MainEvents
    data class DeleteCategory(val category: Category):MainEvents
    data object NavigateToAllBookmarks:MainEvents
    data object NavigateBack:MainEvents
    data object ShowDialogForAddingCategory:MainEvents
    data object HideDialogForAddingCategory:MainEvents
    data class UpdateTittleOfCategory(val tittle:String):MainEvents
}