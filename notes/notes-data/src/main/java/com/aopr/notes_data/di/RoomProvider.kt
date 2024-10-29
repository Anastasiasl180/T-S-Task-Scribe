package com.aopr.notes_data.di

import android.content.Context
import androidx.room.Room
import com.aopr.notes_data.room.AppDatabase
import com.aopr.notes_data.room.notes.NoteDao
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