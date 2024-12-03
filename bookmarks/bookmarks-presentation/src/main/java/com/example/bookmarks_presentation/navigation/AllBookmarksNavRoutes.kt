package com.example.bookmarks_presentation.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface AllBookmarksNavRoutes {

    @Serializable
    data class CreatingBookmarkScreen(val id:Int?): AllBookmarksNavRoutes

}