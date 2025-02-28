package com.example.bookmarks_domain.interactors

import com.aopr.shared_domain.Responses
import com.aopr.shared_domain.resource_manager.SharedStringResourceManager
import com.aopr.shared_domain.throws.EmptyTittleException
import com.example.bookmarks_domain.models.Bookmark
import com.example.bookmarks_domain.models.Category
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flow
import org.koin.core.annotation.Single
import java.io.IOException

@Single
class BookmarksUseCase(private val repository: BookmarksRepository) {

    fun deleteAllBookmarks():Flow<Responses<Unit>> = flow {
        try {
            emit(Responses.Loading())
            val result = repository.deleteAllBookmarks()
            emit(Responses.Success(result))
        } catch (e: IOException) {
            println(e.message.toString())
        } catch (e: Exception) {
            println(e.message.toString())
        }
    }.filter { response ->
        response is Responses.Success || response is Responses.Error
    }

    fun deleteSeveralBookmarks(bookmarks: List<Bookmark>):Flow<Responses<Unit>> = flow {
        try {
            emit(Responses.Loading())
            val result = repository.deleteSeveralBookmarks(bookmarks)
            emit(Responses.Success(result))
        } catch (e: IOException) {
            println(e.message.toString())
        } catch (e: Exception) {
            println(e.message.toString())
        }
    }/*.filter { response ->
        response is Responses.Success || response is Responses.Error
    }*/

fun setBookmarksFromFire(bookmarks:List<Bookmark>?):Flow<Responses<Unit>> = flow {
    try {
        emit(Responses.Loading())
        val result = repository.setBookmarksFromFire(bookmarks)
        emit(Responses.Success(result))
    } catch (e: IOException) {
        println(e.message.toString())
    } catch (e: Exception) {
        println(e.message.toString())
    }
}
    fun createBookmark(bookmark: Bookmark,userId:String?): Flow<Responses<Unit>> = flow {
        try {
            emit(Responses.Loading())
            val result = repository.createBookmark(bookmark,userId)
            emit(Responses.Success(result))
        } catch (e: EmptyTittleException) {
            emit(Responses.Error(SharedStringResourceManager.EmptyTittleMessage.messageId))
        }
        catch (e: IOException) {
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

    fun createCategory(category: Category): Flow<Responses<Unit>> = flow {
        try {
            emit(Responses.Loading())
            val result = repository.createCategory(category)
            emit(Responses.Success(result))
        } catch (e: IOException) {
            println(e.message.toString())
        } catch (e: Exception) {
            println(e.message.toString())
        }
    }

    fun deleteCategory(category: Category): Flow<Responses<Unit>> = flow {
        try {

            emit(Responses.Loading())
            val result = repository.deleteCategory(category)
            emit(Responses.Success(result))

        } catch (e: IOException) {
            println(e.message.toString())
        } catch (e: Exception) {
            println(e.message.toString())
        }
    }
    fun deleteSeveralCategories(category: List<Category>): Flow<Responses<Unit>> = flow {
        try {

            emit(Responses.Loading())
            val result = repository.deleteSeveralCategories(category)
            emit(Responses.Success(result))

        } catch (e: IOException) {
            println(e.message.toString())
        } catch (e: Exception) {
            println(e.message.toString())
        }
    }
    fun getBookmarkById(id:Int):Flow<Responses<Flow<Bookmark>>> = flow {
        try {
            emit(Responses.Loading())
            val result = repository.getBookmarkById(id)
            emit(Responses.Success(result))
        }catch (e: IOException) {
            println(e.message.toString())
        } catch (e: Exception) {
            println(e.message.toString())
        }
    }
    fun getCategoryById(id:Int):Flow<Responses<Flow<Category>>> = flow {
        try {
            emit(Responses.Loading())
            val result = repository.getCategoryById(id)
            emit(Responses.Success(result))
        }catch (e: IOException) {
            println(e.message.toString())
        } catch (e: Exception) {
            println(e.message.toString())
        }
    }

    fun getBookmarksByCategoryId(id:Int?):Flow<Responses<Flow<List<Bookmark>>>> =flow {
        try {
            emit(Responses.Loading())


            val result = repository.getBookmarksByCategoryId(id)
            emit(Responses.Success(result))
          /*  val result = repository.getBookmarksByCategoryId(id).collect(){
                bookmarksLst->
                emit(Responses.Success(bookmarksLst))*/


        }catch (e: IOException) {
            println(e.message.toString())
        } catch (e: Exception) {
            println(e.message.toString())
        }
    }


    fun getAllCategories(): Flow<Responses<Flow<List<Category>>>> = flow {
        try {

            emit(Responses.Loading())
            val result = repository.getAllCategories()
            emit(Responses.Success(result))

        } catch (e: IOException) {
            println(e.message.toString())
        } catch (e: Exception) {
            println(e.message.toString())
        }
    }

}