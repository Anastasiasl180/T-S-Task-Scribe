package com.aopr.notes_data.di

import android.content.Context
import androidx.room.Room
import com.aopr.notes_data.room.NoteDao
import com.aopr.notes_data.room.NotesDatabase
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
