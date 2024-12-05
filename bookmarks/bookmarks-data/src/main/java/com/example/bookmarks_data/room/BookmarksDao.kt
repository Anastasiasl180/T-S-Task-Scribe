package com.example.bookmarks_data.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.bookmarks_domain.models.Bookmark
import com.example.bookmarks_domain.models.Category
import kotlinx.coroutines.flow.Flow

@Dao
interface BookmarksDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveBookmark(bookmarksEntity: BookmarksEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveCategory(category:CategoryEntity)

    @Update
    suspend fun updateBookmark(bookmark: BookmarksEntity)

    @Delete
    suspend fun deleteCategory(category:CategoryEntity)

    @Query("SELECT * FROM categories")
    fun getAllCategories():Flow<List<CategoryEntity>>

   /* @Query("SELECT * FROM CATEGORIES WHERE id = :id")
    fun getBookmarksByCategoryId(id:Int):Flow<List<BookmarksEntity>>
*/
 /*   @Query("SELECT * FROM categories WHERE id = :categoryId")
    fun getBookmarksByCategoryId(categoryId: Int): Flow<List<BookmarksEntity>>*/

    @Query("SELECT * FROM bookmarks WHERE categoryId = :categoryId")
    fun getBookmarksByCategoryId(categoryId: Int): Flow<List<BookmarksEntity>>


    @Query("SELECT * FROM bookmarks WHERE id = :id LIMIT 1 ")
   fun getBookmarkById(id:Int):Flow<BookmarksEntity>

    @Query("SELECT * FROM bookmarks")
    fun getAllBookmarks():Flow<List<BookmarksEntity>>



}