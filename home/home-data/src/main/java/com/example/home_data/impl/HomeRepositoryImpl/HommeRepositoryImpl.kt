package com.example.home_data.impl.HomeRepositoryImpl

import android.util.Log
import com.aopr.firebase_domain.firestore_user_data.FireUser
import com.aopr.notes_domain.interactors.NotesUseCase
import com.aopr.tasks_domain.interactors.TasksUseCase
import com.example.bookmarks_domain.interactors.BookmarksUseCase
import com.example.home_domain.HomeRepository.HommeRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import org.koin.core.annotation.Single

@Single
class HommeRepositoryImpl(
    private val notesUseCase: NotesUseCase,
    private val tasksUseCase: TasksUseCase,
    private val bookmarksUseCase: BookmarksUseCase,
    private val fireUser: FireUser
) : HommeRepository {
    override suspend fun deleteAllDataFromRoom(
    ) {
        val coroutineScope = CoroutineScope(currentCoroutineContext())
        coroutineScope.launch {
            try {
                val deleteOperation = listOf(
                    async { bookmarksUseCase.deleteAllBookmarks().first() },
                    async { tasksUseCase.deleteAllTasks().first() },
                    async { notesUseCase.deleteAllNotes().first() }
                )
                deleteOperation.awaitAll()
                fireUser.clearData()
            } catch (e: Exception) {
                println(e.message)
            }
        }

    }

    private suspend fun FireUser.clearData(): FireUser {
        this.userId = null
        this.listOfTasks = null
        this.listOfBookmarks = null
        this.listOfNotes = null
        this.settings = null
        return this
    }
}
