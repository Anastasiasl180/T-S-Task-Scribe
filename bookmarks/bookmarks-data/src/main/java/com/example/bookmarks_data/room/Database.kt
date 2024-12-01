package com.example.bookmarks_data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import org.koin.core.annotation.Single


@Single
fun provideBookmarksDatabase(context: Context) {
    Room.databaseBuilder(context,
        BookmarksDatabase::class.java,
        "bookmarks-database").build()
}

@Single
fun provideBookmarksDao(bookmarksDatabase: BookmarksDatabase): BookmarksDao {
    return bookmarksDatabase.bookmarksDao()
}


@Database(entities = [BookmarksEntity::class,CategoryEntity::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class BookmarksDatabase : RoomDatabase() {
    abstract fun bookmarksDao(): BookmarksDao
}