package com.example.bookmarks_domain.interactors

import com.example.bookmarks_domain.models.Bookmark
import com.example.bookmarks_domain.models.Category
import kotlinx.coroutines.flow.Flow

interface BookmarksRepository {
    suspend fun createBookmark(bookmark: Bookmark)
    suspend fun updateBookmark(bookmark: Bookmark)
    suspend fun deleteBookmark(bookmark: Bookmark)
    suspend fun getBookmarkById(id:Int): Flow<Bookmark>
    suspend fun getAllBookmarks():Flow<List<Bookmark>>
    suspend fun createCategory(category: Category)
    suspend fun deleteCategory(category: Category)
    suspend fun getBookmarksByCategoryId(id:Int?): Flow<List<Bookmark>>
    suspend fun getAllCategories():Flow<List<Category>>

}