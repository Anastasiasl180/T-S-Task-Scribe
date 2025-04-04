package com.example.bookmarks_data.impl

import android.content.Context
import com.aopr.firebase_data.helpers.Helpers
import com.aopr.shared_domain.internetConnectionFun.isInternetAvailable
import com.aopr.shared_domain.throws.EmptyTittleException
import com.example.bookmarks_data.mapper.mapToBookmark
import com.example.bookmarks_data.mapper.mapToCategory
import com.example.bookmarks_data.mapper.mapToEntity
import com.example.bookmarks_data.room.BookmarksDao
import com.example.bookmarks_domain.interactors.BookmarksRepository
import com.example.bookmarks_domain.models.Bookmark
import com.example.bookmarks_domain.models.Category
import com.example.bookmarks_domain.models.toFirestoreMap
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import org.koin.core.annotation.Single

@Single
class BookmarksRepositoryImpl(private val dao: BookmarksDao, private val context: Context) :
    BookmarksRepository {
    override suspend fun deleteAllBookmarks() {
        val allBookmarks = dao.getAllBookmarks().first()
        dao.deleteAllBookmarks(allBookmarks)
    }

    override suspend fun createBookmark(bookmark: Bookmark, userId: String?) {
        val existingBookmark = dao.getBookmarkById(bookmark.id).firstOrNull()
        if (existingBookmark != null) {
            updateBookmark(bookmark, userId)
            if (isInternetAvailable(context)) {
                val listUpdated = getAllBookmarks().first()
                val firestoreData = listUpdated.map { it.toFirestoreMap() }
                Helpers.FirebaseHelper.updateUserDataBookmark(
                    userId,
                    mapOf("listOfBookmarks" to firestoreData)
                )

            }
        } else {
            if (bookmark.tittle.isNotEmpty()) {
                dao.saveBookmark(bookmark.mapToEntity())
                if (isInternetAvailable(context)) {
                    val listUpdated =
                        getAllBookmarks().first()
                    val firestoreData = listUpdated.map { it.toFirestoreMap() }
                   Helpers.FirebaseHelper.updateUserDataBookmark(
                        userId,
                        mapOf("listOfBookmarks" to firestoreData)
                    )

                }
            }else{
                throw EmptyTittleException()
            }
        }


    }

    override suspend fun setBookmarksFromFire(bookmarks: List<Bookmark>?) {
        if (bookmarks != null) {
            dao.saveBookmarks(bookmarks.map { it.mapToEntity() })
        } else {
          }
    }

    override suspend fun updateBookmark(bookmark: Bookmark, userId: String?) {
        if (bookmark.tittle.isNotEmpty()) {
            dao.updateBookmark(bookmark.mapToEntity())
        }else{
            throw EmptyTittleException()
        }
    }

    override suspend fun deleteBookmark(bookmark: Bookmark) {
        dao.deleteBookmark(bookmark.mapToEntity())
    }

    override suspend fun deleteSeveralBookmarks(bookmarks: List<Bookmark>) {
    dao.deleteSeveralBookmark(bookmarks.map { it.mapToEntity() })
    }

    override suspend fun getBookmarkById(id: Int): Flow<Bookmark> {
        return dao.getBookmarkById(id).map { entity ->
            entity.mapToBookmark()
        }
    }

    override suspend fun getAllBookmarks(): Flow<List<Bookmark>> {
        return dao.getAllBookmarks().map { list ->
            list.map { entity ->
                entity.mapToBookmark()
            }
        }
    }


    override suspend fun createCategory(category: Category) {
        if (category.tittle.isNotEmpty()) {
            dao.saveCategory(category.mapToEntity())
        }
    }

    override suspend fun deleteCategory(category: Category) {
        dao.deleteCategory(category.mapToEntity())
    }

    override suspend fun deleteSeveralCategories(category: List<Category>) {
        dao.deleteSeveralCategories(category.map { it.mapToEntity() })
    }

    override suspend fun getBookmarksByCategoryId(id: Int?): Flow<List<Bookmark>> {
        return id?.let { nonNullId ->
            dao.getBookmarksByCategoryId(nonNullId).map { entityList ->
                entityList.map { entity ->
                    entity.mapToBookmark()
                }

            }

        } ?: flowOf(emptyList())
    }

    override suspend fun getCategoryById(id: Int): Flow<Category> {
      return dao.getCategoryById(id).map { it.mapToCategory() }
    }
    /*
       return dao.getBookmarksByCategoryId(id).map {entityList->
             entityList.map { entity ->
                 entity.mapToBookmark()
             }

         }*/


    override suspend fun getAllCategories(): Flow<List<Category>> {
        return dao.getAllCategories().map { list ->
            list.map { entity ->
                entity.mapToCategory()
            }
        }
    }

}