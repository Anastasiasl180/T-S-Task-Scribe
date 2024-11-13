package com.aopr.notes_data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import org.koin.core.annotation.Single


@Single
fun provideAppDataBase(context: Context): AppDatabase {
    return Room.databaseBuilder(
        context = context,
        AppDatabase::class.java,
        "app-database"
    ).build()
}

@Single
fun provideNoteDao(appDataBase: AppDatabase): NoteDao {
    return appDataBase.noteDao()
}


@Database(entities = [NoteEntity::class], version = 1)
abstract class AppDatabase: RoomDatabase(){
    abstract fun noteDao(): NoteDao
}