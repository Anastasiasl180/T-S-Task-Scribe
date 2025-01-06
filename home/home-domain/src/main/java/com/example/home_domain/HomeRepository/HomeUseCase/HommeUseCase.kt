package com.example.home_domain.HomeRepository.HomeUseCase

import android.util.Log
import com.aopr.notes_domain.interactors.NotesUseCase
import com.aopr.shared_domain.Responses
import com.aopr.tasks_domain.interactors.TasksUseCase
import com.example.bookmarks_domain.interactors.BookmarksUseCase
import com.example.home_domain.HomeRepository.HommeRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.launch
import org.koin.core.annotation.Single
import java.io.IOException

@Single
class HommeUseCase(
    private val hommeRepository: HommeRepository
) {
    fun deleteAllUserData(): Flow<Responses<Unit>> = flow {
        try {
            emit(Responses.Loading())
            val result = hommeRepository.deleteAllDataFromRoom()
            emit(Responses.Success(result))
        } catch (e: IOException) {

        } catch (e: Exception) {

        }

    }
}