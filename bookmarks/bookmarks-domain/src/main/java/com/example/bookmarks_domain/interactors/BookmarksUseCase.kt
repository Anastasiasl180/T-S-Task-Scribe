package com.example.bookmarks_domain.interactors

import com.aopr.shared_domain.Responses
import com.example.bookmarks_domain.models.Bookmark
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.koin.core.annotation.Single
import java.io.IOException

@Single
class BookmarksUseCase(private val repository: BookmarksRepository) {

    fun createBookmark(bookmark: Bookmark): Flow<Responses<Unit>> = flow {
        try {
            emit(Responses.Loading())
            val result = repository.createBookmark(bookmark)
            emit(Responses.Success(result))
        } catch (e: IOException) {
            println(e.message.toString())
        } catch (e: Exception) {
            println(e.message.toString())
        }

    }

    fun getAllBookmarks(): Flow<Responses<Flow<List<Bookmark>>>> = flow {
        try {
            emit(Responses.Loading())
            val result = repository.getAllBookmarks()
            emit(Responses.Success(result))
        } catch (e: IOException) {
            println(e.message.toString())
        } catch (e: Exception) {
            println(e.message.toString())
        }
    }

    fun deleteBookmark(bookmark: Bookmark): Flow<Responses<Unit>> = flow {
        try {
            emit(Responses.Loading())
            val result = repository.deleteBookmark(bookmark)
            emit(Responses.Success(result))
        } catch (e: IOException) {
            println(e.message.toString())
        } catch (e: Exception) {
            println(e.message.toString())
        }
    }

}