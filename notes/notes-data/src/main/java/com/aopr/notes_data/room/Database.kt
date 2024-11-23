package com.aopr.notes_data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import org.koin.core.annotation.Single





@Database(entities = [NoteEntity::class], version = 1, exportSchema = false)
abstract class NotesDatabase: RoomDatabase(){
    abstract fun noteDao(): NoteDao
}