package com.aopr.authentication_domain.fier_store_uxer_data

import com.aopr.notes_domain.models.Note
import com.aopr.shared_domain.inter.SettingsDto
import com.aopr.tasks_domain.models.Task
import com.example.bookmarks_domain.models.Bookmark
import com.google.android.gms.tasks.Tasks

data class FireUser(
    val listOfTasks: List<Task>? = null,
    val listOfBookmarks: List<Bookmark>? = null,
    val listOfNotes: List<Note>? = null,
    val settings: SettingsDto? = null
)