package com.aopr.firebase_domain.interact

import com.aopr.notes_domain.models.Note
import com.aopr.shared_domain.Responses
import com.aopr.shared_domain.inter.SettingsDto
import com.aopr.tasks_domain.models.Task
import com.example.bookmarks_domain.models.Bookmark
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.koin.core.annotation.Single

@Single
class FirebaseUseCase(private val repository: FirebaseRepository) {

    fun updateUserDataBookmark(
        id: String,
        updates: Map<String, List<Bookmark>>
    ): Flow<Responses<Unit>> = flow {

        try {
            emit(Responses.Loading())
            val data = repository.updateUserDataBookmark(id, updates)
        } catch (e: Exception) {

        }

    }

    fun updateUserDataTask(
        id: String,
        updates: Map<String, List<Task>>
    ): Flow<Responses<Unit>> = flow {

        try {
            emit(Responses.Loading())
            val data = repository.updateUserDataTask(id, updates)
        } catch (e: Exception) {

        }

    }

    fun updateUserDataNote(
        id: String,
        updates: Map<String, List<Note>>
    ): Flow<Responses<Unit>> = flow {

        try {
            emit(Responses.Loading())
            val data = repository.updateUserDataNote(id, updates)
        } catch (e: Exception) {

        }

    }

    fun updateUserDataSettingsDto(
        id: String,
        updates: Map<String, List<SettingsDto>>
    ): Flow<Responses<Unit>> = flow {

        try {
            emit(Responses.Loading())
            val data = repository.updateUserDataSettingsDto(id, updates)
        } catch (e: Exception) {

        }

    }


}