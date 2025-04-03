package com.example.bookmarks_domain.models

import android.util.Log
import androidx.room.Entity
import androidx.room.PrimaryKey

data class Category(
    val id: Int = 0,
    val tittle: String,
    val listOfBookmarks: List<Bookmark>? = null
)

data class Bookmark(
    val id: Int = 0,
    val tittle: String,
    val url: String? = null,
    val fileUri: String? = null,
    val gptDialog: String? = null,
    val categoryId: Int? = null
){
    fun toFirestore(): Map<String, Any?> {
        return mapOf(
            "id" to id,
            "tittle" to tittle,
            "url" to url,
            "fileUri" to fileUri,
            "gptDialog" to gptDialog,
            "categoryId" to categoryId
        ).also {
        }
    }

    companion object {
        fun fromFirestore(data: Map<String, Any?>): Bookmark {
            return Bookmark(
                id = (data["id"] as? Long)?.toInt() ?: 0,
                tittle = data["tittle"] as? String ?: "",
                url = data["url"] as? String,
                fileUri = data["fileUri"] as? String,
                gptDialog = data["gptDialog"] as? String,
                categoryId = (data["categoryId"] as? Long)?.toInt()
            )
        }
    }
}
