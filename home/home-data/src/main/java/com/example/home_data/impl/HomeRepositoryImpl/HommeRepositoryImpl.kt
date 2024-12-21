package com.example.home_data.impl.HomeRepositoryImpl

import com.aopr.notes_data.room.NoteDao
import com.aopr.tasks_data.room.TasksDao
import com.example.bookmarks_data.room.BookmarksDao
import com.example.home_domain.HomeRepository.HommeRepository
import kotlinx.coroutines.flow.first
import org.koin.core.annotation.Single

@Single
class HommeRepositoryImpl(
    private val taskDao: TasksDao,
    private val noteDao: NoteDao,
    private val bookmarksDao: BookmarksDao
) : HommeRepository {
    override suspend fun deleteAllDataFromRoom(
    ) {

        val listOfBookmarks = bookmarksDao.getAllBookmarks().first()
        val listOfNotes = noteDao.getALlNotes().first()
        val listOfTask = taskDao.getALlTasks().first()
        bookmarksDao.deleteAllBookmark(listOfBookmarks)
        noteDao.deleteAllNote(listOfNotes)
        taskDao.deleteAllTask(listOfTask)


    }
}