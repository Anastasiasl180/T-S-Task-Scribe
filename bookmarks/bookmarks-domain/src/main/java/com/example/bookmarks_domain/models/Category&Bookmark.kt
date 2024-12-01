package com.example.bookmarks_domain.models

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
)