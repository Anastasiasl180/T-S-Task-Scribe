package com.aopr.notes_domain.interactors

import com.aopr.notes_domain.models.Note
import kotlinx.coroutines.flow.Flow

interface NotesRepository{

    suspend fun deleteAllNotes()
    suspend fun updateNote(note:Note)
    suspend fun createNote(note: Note)
    suspend fun getALlNotes():Flow<List<Note>>
    suspend fun getNoteById(id:Int):Flow<Note>
    suspend fun deleteChosenNotes(note:List<Note>)
    suspend fun setNotesFromFire(notes: List<Note>?)

}