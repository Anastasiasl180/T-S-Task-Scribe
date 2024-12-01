package com.example.bookmarks_presentation.navigation

import kotlinx.serialization.Serializable


@Serializable
sealed interface AllCategoriesOfBookmarksNavRoutes {

    @Serializable
    data object AllBookmarksScreen:AllCategoriesOfBookmarksNavRoutes

    @Serializable
    data class AllBookmarksInCategory(val id:Int):AllCategoriesOfBookmarksNavRoutes

    @Serializable
    data class CreatingBookMarkScreen(val id:Int?):AllCategoriesOfBookmarksNavRoutes

}