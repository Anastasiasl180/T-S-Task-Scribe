package com.aopr.notes_data.impl

import com.aopr.notes_data.mapper.mapToEntity
import com.aopr.notes_data.mapper.mapToNote
import com.aopr.notes_data.room.NoteDao
import com.aopr.notes_domain.interactors.NotesRepository
import com.aopr.notes_domain.models.Note
import com.aopr.shared_domain.throws.EmptyDescriptionException
import com.aopr.shared_domain.throws.EmptyTittleException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import org.koin.core.annotation.Single

@Single
class NotesRepositoryImpl(private val dao: NoteDao) : NotesRepository {
    override suspend fun createNote(note: Note) {
        if (note.description.isBlank()) throw EmptyDescriptionException()

        if (note.tittle.isBlank()) throw EmptyTittleException()

        if (note.tittle.isBlank() && note.description.isBlank()) throw EmptyTittleException()

        val existingId = dao.getNoteById(note.id).firstOrNull()
        if (existingId != null) {
            dao.updateNote(note.mapToEntity().copy(timestamp = System.currentTimeMillis()))
        } else {
            dao.insertNote(note.mapToEntity().copy(timestamp = System.currentTimeMillis()))
        }

    }

    override suspend fun setNotesFromFire(notes: List<Note>?) {
        if (notes != null) {
            dao.saveListOfNotes(notes.map { it.mapToEntity() })
        }
    }

    override suspend fun deleteNote(note: Note) {
        dao.deleteNote(note.mapToEntity())
    }

    override suspend fun getNoteById(id: Int): Flow<Note> {
        return dao.getNoteById(id).map { it.mapToNote() }
    }

    override suspend fun getALlNotes(): Flow<List<Note>> {
        return dao.getALlNotes().map { list -> list.map { entity -> entity.mapToNote() } }
    }

    override suspend fun updateNote(note: Note) {
        val existingNote = dao.getNoteById(note.id).firstOrNull()
        if (existingNote != null) {
            val updatedNote = note.copy(isPinned = !note.isPinned)
            dao.updateNote(updatedNote.mapToEntity())
        }
    }
}