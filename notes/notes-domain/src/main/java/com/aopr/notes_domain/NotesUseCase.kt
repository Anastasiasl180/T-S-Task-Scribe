package com.aopr.notes_domain

import android.util.Log
import com.aopr.notes_domain.models.Note
import com.aopr.notes_domain.throws.EmptyDescriptionException
import com.aopr.notes_domain.throws.EmptyTittleException
import com.aopr.shared_domain.Responses
import com.aopr.shared_domain.resource_manager.SharedStringResourceManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.koin.core.annotation.Single
import java.io.IOException

@Single
class NotesUseCase(private val repository: NotesRepository) {

    fun createNote(note: Note): Flow<Responses<Unit>> = flow {
        try {
            emit(Responses.Loading())
            val data = repository.createNote(note)
            emit(Responses.Success(data))
        } catch (e: IOException) {
            emit(Responses.Error(SharedStringResourceManager.DefaultMessage.messageId))
        } catch (e: EmptyTittleException) {
            emit(Responses.Error(R.string.TittleExceptionMessage))
        } catch (e: EmptyDescriptionException) {
            emit(Responses.Error(R.string.DescriptionExceptionMessage))
        }
    }

    fun deleteNote(note: Note): Flow<Responses<Unit>> = flow {
        try {
            emit(Responses.Loading())
            val data = repository.deleteNote(note)
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

}