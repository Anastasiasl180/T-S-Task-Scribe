package com.aopr.shared_data.di.room

import android.content.Context
import androidx.room.Room
import com.aopr.notes_data.room.NoteDao
import com.aopr.tasks_data.room.TasksDao
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
@Single
fun provideTaskDao(appDataBase: AppDatabase):TasksDao {
    return appDataBase.taskDao()
}