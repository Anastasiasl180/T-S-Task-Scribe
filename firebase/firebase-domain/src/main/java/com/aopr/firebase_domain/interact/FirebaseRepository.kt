package com.aopr.firebase_domain.interact

import com.aopr.notes_domain.models.Note
import com.aopr.shared_domain.inter.SettingsDto
import com.aopr.tasks_domain.models.Task
import com.example.bookmarks_domain.models.Bookmark

interface FirebaseRepository {
    suspend fun updateUserDataBookmark(id: String, updates: Map<String, List<Bookmark>>)
    suspend fun updateUserDataTask(id: String, updates: Map<String, List<Task>>)
    suspend fun updateUserDataNote(id: String, updates: Map<String, List<Note>>)
    suspend fun updateUserDataSettingsDto(id: String, updates: Map<String, List<SettingsDto>>)
}