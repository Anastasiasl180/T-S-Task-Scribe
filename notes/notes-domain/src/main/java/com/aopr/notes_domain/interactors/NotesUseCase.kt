package com.aopr.notes_domain.interactors

import com.aopr.notes_domain.models.Note
import com.aopr.shared_domain.Responses
import com.aopr.shared_domain.resource_manager.SharedStringResourceManager
import com.aopr.shared_domain.throws.EmptyDescriptionException
import com.aopr.shared_domain.throws.EmptyTittleException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.koin.core.annotation.Single
import java.io.IOException

@Single
class NotesUseCase(private val repository: NotesRepository) {

    fun deleteAllNotes(): Flow<Responses<Unit>> = flow {
        try {
            emit(Responses.Loading())
            val data = repository.deleteAllNotes()
            emit(Responses.Success(data))
        } catch (e: IOException) {
            emit(Responses.Error(SharedStringResourceManager.DefaultMessage.messageId))
        }
    }

    fun setNotesFromFire(notes: List<Note>?): Flow<Responses<Unit>> = flow {
        try {
            emit(Responses.Loading())
            val data = repository.setNotesFromFire(notes)
            emit(Responses.Success(data))
        } catch (e: IOException) {
            emit(Responses.Error(SharedStringResourceManager.DefaultMessage.messageId))
        }
    }

    fun createNote(note: Note): Flow<Responses<Unit>> = flow {
        try {
            emit(Responses.Loading())
            val data = repository.createNote(note)
            emit(Responses.Success(data))
        } catch (e: IOException) {
            emit(Responses.Error(SharedStringResourceManager.DefaultMessage.messageId))
        } catch (e: EmptyTittleException) {
            emit(Responses.Error(SharedStringResourceManager.EmptyTittleMessage.messageId))
        } catch (e: EmptyDescriptionException) {
            emit(Responses.Error(SharedStringResourceManager.EmptyDescriptionMessage.messageId))
        }
    }

    fun deleteNote(note: List<Note>): Flow<Responses<Unit>> = flow {
        try {
            emit(Responses.Loading())
            val data = repository.deleteChosenNotes(note)
            emit(Responses.Success(data))
        } catch (e: IOException) {
            emit(Responses.Error(SharedStringResourceManager.DefaultMessage.messageId))
        } catch (e: Exception) {
            emit(Responses.Error(SharedStringResourceManager.DefaultMessage.messageId))
        }
    }

    fun getNoteById(id: Int): Flow<Responses<Flow<Note>>> = flow {
        try {
            emit(Responses.Loading())
            val data = repository.getNoteById(id)
            emit(Responses.Success(data))
        } catch (e: IOException) {
            emit(Responses.Error(SharedStringResourceManager.DefaultMessage.messageId))
        } catch (e: Exception) {
            emit(Responses.Error(SharedStringResourceManager.DefaultMessage.messageId))
        }
    }

    fun getAllNotes(): Flow<Responses<Flow<List<Note>>>> = flow {
        try {
            emit(Responses.Loading())
            val data = repository.getALlNotes()
            emit(Responses.Success(data))

        } catch (e: IOException) {
            emit(Responses.Error(SharedStringResourceManager.DefaultMessage.messageId))
        } catch (e: Exception) {
            emit(Responses.Error(SharedStringResourceManager.DefaultMessage.messageId))
        }
    }

    fun updatedNote(note: Note): Flow<Responses<Unit>> = flow {
        try {
            emit(Responses.Loading())
            val data = repository.updateNote(note)
            emit(Responses.Success(data))
        } catch (e: IOException) {
            emit(Responses.Error(SharedStringResourceManager.DefaultMessage.messageId))
        } catch (e: Exception) {
            emit(Responses.Error(SharedStringResourceManager.DefaultMessage.messageId))
        }catch (e: EmptyTittleException) {
            emit(Responses.Error(SharedStringResourceManager.EmptyTittleMessage.messageId))
        } catch (e: EmptyDescriptionException) {
            emit(Responses.Error(SharedStringResourceManager.EmptyDescriptionMessage.messageId))
        }
    }

}