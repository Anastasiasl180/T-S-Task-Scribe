package com.aopr.shared_data.di.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.aopr.notes_data.room.NoteDao
import com.aopr.notes_data.room.NoteEntity
import com.aopr.tasks_data.room.Converts
import com.aopr.tasks_data.room.TaskEntity
import com.aopr.tasks_data.room.TasksDao

@Database(entities = [NoteEntity::class,TaskEntity::class], version = 1)
@TypeConverters(Converts::class)
abstract class AppDatabase:RoomDatabase(){
    abstract fun noteDao(): NoteDao
    abstract fun taskDao():TasksDao
}