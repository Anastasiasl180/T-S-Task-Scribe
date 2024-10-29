package com.aopr.notes_data

import com.aopr.notes_data.mapper.mapToEntity
import com.aopr.notes_data.mapper.mapToNote
import com.aopr.notes_data.room.notes.NoteDao
import com.aopr.notes_domain.NotesRepository
import com.aopr.notes_domain.models.Note
import com.aopr.notes_domain.throws.EmptyDescriptionException
import com.aopr.notes_domain.throws.EmptyTittleException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.koin.core.annotation.Single

@Single
class NotesRepositoryImpl(private val dao: NoteDao) : NotesRepository {
    override suspend fun createNote(note: Note) {
        if (note.description.isBlank()) throw EmptyDescriptionException()

        if (note.tittle.isBlank()) throw EmptyTittleException()

        dao.insertNote(note.mapToEntity())

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
}