package com.aopr.firebase_domain.firestore_user_data

import com.aopr.notes_domain.models.Note
import com.aopr.tasks_domain.models.Task
import com.example.bookmarks_domain.models.Bookmark
import org.koin.core.annotation.Singleton

@Singleton
class FireUser(
    var userId: String? = null,
    var userName:String? = null,
    var userPicture:String? = null,
    var listOfTasks: List<Task>? = null,
    var listOfBookmarks: List<Bookmark>? = null,
    var listOfNotes: List<Note>? = null,
)