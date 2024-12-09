package com.example.bookmarks_presentation.navigation

import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import androidx.navigation.NavType
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json


@Serializable
sealed interface BookmarksNavRoutes {

    @Serializable
    data object AllBookmarksScreen : BookmarksNavRoutes

    @Serializable
    data class AllBookmarksInCategory(val id: Int) : BookmarksNavRoutes

    @Serializable
    data class CreatingBookMarkScreen(val bookmarksInfo: BookmarksRoutesInfo) : BookmarksNavRoutes

}

@Serializable
@Parcelize
data class BookmarksRoutesInfo(
    val categoryId: Int? = null,
    val bookmarkId: Int? = null
) : Parcelable

fun getBookmarksTypeMap(): NavType<BookmarksRoutesInfo> {
    return object : NavType<BookmarksRoutesInfo>(true) {
        override fun get(
            bundle: Bundle,
            key: String
        ): BookmarksRoutesInfo? {
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                bundle.getParcelable<BookmarksRoutesInfo>(key, BookmarksRoutesInfo::class.java)
            } else {
                bundle.getParcelable(key)
            }
        }

        override fun parseValue(value: String): BookmarksRoutesInfo {
            return Json.decodeFromString(value)
        }

        override fun put(
            bundle: Bundle,
            key: String,
            value: BookmarksRoutesInfo
        ) {
            bundle.putParcelable(key, value)
        }

        override fun serializeAsValue(value: BookmarksRoutesInfo): String {
            return Json.encodeToString(
                serializer = BookmarksRoutesInfo.serializer(),value
            )
        }

    }
}