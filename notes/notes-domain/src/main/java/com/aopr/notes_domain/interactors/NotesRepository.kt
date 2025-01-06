package com.aopr.notes_domain.interactors

import com.aopr.notes_domain.models.Note
import kotlinx.coroutines.flow.Flow

interface NotesRepository{
    suspend fun createNote(note: Note)
    suspend fun setNotesFromFire(notes: List<Note>?)
    suspend fun deleteNote(note:Note)
    suspend fun deleteAllNotes()
    suspend fun getNoteById(id:Int):Flow<Note>
    suspend fun getALlNotes():Flow<List<Note>>
    suspend fun updateNote(note:Note)
}