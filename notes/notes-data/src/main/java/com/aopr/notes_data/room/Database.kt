package com.aopr.notes_data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import org.koin.core.annotation.Single


@Single
fun provideNotesDataBase(context: Context): NotesDatabase {
    return Room.databaseBuilder(
        context = context,
        NotesDatabase::class.java,
        "notes-database"
    ).build()
}

@Single
fun provideNoteDao(notesDataBase: NotesDatabase): NoteDao {
    return notesDataBase.noteDao()
}


@Database(entities = [NoteEntity::class], version = 1, exportSchema = false)
abstract class NotesDatabase: RoomDatabase(){
    abstract fun noteDao(): NoteDao
}