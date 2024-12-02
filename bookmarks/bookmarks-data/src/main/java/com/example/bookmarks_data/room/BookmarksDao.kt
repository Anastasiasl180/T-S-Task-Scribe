package com.example.bookmarks_data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface BookmarksDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveBookmark(bookmarksEntity: BookmarksEntity)

    @Query("SELECT * FROM bookmarks WHERE id = :id LIMIT 1 ")
   fun getBookmarkById(id:Int):Flow<BookmarksEntity>

    @Query("SELECT * FROM bookmarks")
    fun getAllBookmarks():Flow<List<BookmarksEntity>>



}