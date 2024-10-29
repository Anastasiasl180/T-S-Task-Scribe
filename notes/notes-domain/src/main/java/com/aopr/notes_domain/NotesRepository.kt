package com.aopr.notes_domain

import com.aopr.notes_domain.models.Note
import kotlinx.coroutines.flow.Flow

interface NotesRepository{
    suspend fun createNote(note: Note)
    suspend fun deleteNote(note:Note)
    suspend fun getNoteById(id:Int):Flow<Note>
    suspend fun getALlNotes():Flow<List<Note>>
}