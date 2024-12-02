package com.example.bookmarks_domain.interactors

import com.example.bookmarks_domain.models.Bookmark
import kotlinx.coroutines.flow.Flow

interface BookmarksRepository {
    suspend fun createBookmark(bookmark: Bookmark)
    suspend fun deleteBookmark(bookmark: Bookmark)
    suspend fun getBookmarkById(id:Int): Flow<Bookmark>
    suspend fun getAllBookmarks():Flow<List<Bookmark>>
}