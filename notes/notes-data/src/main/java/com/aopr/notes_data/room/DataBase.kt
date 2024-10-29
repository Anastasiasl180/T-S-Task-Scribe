package com.aopr.notes_data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.aopr.notes_data.mapper.mapToEntity
import com.aopr.notes_data.room.notes.NoteDao
import com.aopr.notes_data.room.notes.NoteEntity
import com.aopr.notes_domain.models.Note

@Database(entities = [NoteEntity::class], version = 1)
abstract class AppDatabase:RoomDatabase(){
    abstract fun noteDao(): NoteDao
}