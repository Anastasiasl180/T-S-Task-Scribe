package com.example.bookmarks_data.impl

import com.example.bookmarks_data.mapper.mapToBookmark
import com.example.bookmarks_data.mapper.mapToEntity
import com.example.bookmarks_data.room.BookmarksDao
import com.example.bookmarks_domain.interactors.BookmarksRepository
import com.example.bookmarks_domain.models.Bookmark
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.koin.core.annotation.Single

@Single
class BookmarksRepositoryImpl(private val dao: BookmarksDao) : BookmarksRepository {
    override suspend fun createBookmark(bookmark: Bookmark) {
        if (bookmark.tittle.isNotEmpty()) {
            dao.saveBookmark(bookmark.mapToEntity())
        }
    }

    override suspend fun deleteBookmark(bookmark: Bookmark) {
        TODO()
    }

    override suspend fun getBookmarkById(id: Int): Flow<Bookmark> {
        TODO()
    }

    override suspend fun getAllBookmarks(): Flow<List<Bookmark>> {
        return dao.getAllBookmarks().map { list ->
            list.map { entity ->
                entity.mapToBookmark()
            }
        }
    }
}