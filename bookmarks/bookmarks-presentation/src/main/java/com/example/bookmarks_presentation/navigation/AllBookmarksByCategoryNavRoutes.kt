package com.example.bookmarks_presentation.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface AllBookmarksByCategoryNavRoutes {
    @Serializable
    data class CreatingBookmarkWithCategoryId(val id:Int?): AllBookmarksByCategoryNavRoutes
}