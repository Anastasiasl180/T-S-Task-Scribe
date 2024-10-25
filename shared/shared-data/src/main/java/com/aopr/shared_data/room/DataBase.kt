package com.aopr.shared_data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.aopr.shared_data.room.notes.NoteDao
import com.aopr.shared_data.room.notes.NoteEntity

@Database(entities = [NoteEntity::class], version = 1)
abstract class AppDatabase:RoomDatabase(){
    abstract fun noteDao():NoteDao
}