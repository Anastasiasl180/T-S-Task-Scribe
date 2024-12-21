package com.aopr.firebase_data.helpers

import androidx.lifecycle.viewModelScope
import com.aopr.firebase_domain.firestore_user_data.FireUser
import com.aopr.notes_domain.interactors.NotesUseCase
import com.aopr.notes_domain.models.Note
import com.aopr.shared_domain.Responses
import com.aopr.shared_domain.inter.SettingsDto
import com.aopr.tasks_domain.interactors.TasksUseCase
import com.aopr.tasks_domain.models.Task
import com.example.bookmarks_domain.interactors.BookmarksUseCase
import com.example.bookmarks_domain.models.Bookmark
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.tasks.await



    object FirebaseHelper {
        private lateinit var auth: FirebaseAuth

        private val personCollectionRef = Firebase.firestore.collection("users")

        suspend fun updateUserDataBookmark(
            id: String?,
            updates: Map<String, List<Map<String, Any?>>>
        ) {
            try {
                val querySnapshot = personCollectionRef.whereEqualTo("userId", id)
                    .get()
                    .await()
                val document = querySnapshot.documents.firstOrNull()

                if (document != null) {
                    personCollectionRef.document(document.id).update(updates).await()

                }
            } catch (e: Exception) {
                println(e.message.toString())
            }
        }

        suspend fun updateUserDataTask(id: String, updates: Map<String, List<Task>>) {
            try {
                val querySnapshot = personCollectionRef.whereEqualTo("userId", id)
                    .get()
                    .await()
                val document = querySnapshot.documents.firstOrNull()

                if (document != null) {
                    personCollectionRef.document(document.id).update(updates).await()

                }
            } catch (e: Exception) {
                println(e.message.toString())
            }
        }

        suspend fun updateUserDataNote(id: String, updates: Map<String, List<Note>>) {
            try {
                val querySnapshot = personCollectionRef.whereEqualTo("userId", id)
                    .get()
                    .await()
                val document = querySnapshot.documents.firstOrNull()

                if (document != null) {
                    personCollectionRef.document(document.id).update(updates).await()

                }
            } catch (e: Exception) {
                println(e.message.toString())
            }
        }

        suspend fun updateUserDataSettingsDto(
            id: String,
            updates: Map<String, List<SettingsDto>>
        ) {
            try {
                val querySnapshot = personCollectionRef.whereEqualTo("userId", id)
                    .get()
                    .await()
                val document = querySnapshot.documents.firstOrNull()

                if (document != null) {
                    personCollectionRef.document(document.id).update(updates).await()

                }
            } catch (e: Exception) {
                println(e.message.toString())
            }
        }

        suspend fun getAllBookmarks(userId: String): List<Bookmark> {
            return try {
                val querySnapshot = personCollectionRef.whereEqualTo("userId", userId)
                    .get()
                    .await()
                val document = querySnapshot.documents.firstOrNull()

                if (document != null) {
                    val bookmarks = document.get("bookmarks") as? List<Map<String, Any?>>
                    bookmarks?.map { Bookmark.fromFirestore(it) } ?: emptyList()
                } else {
                    emptyList()
                }
            } catch (e: Exception) {
                println("Error fetching bookmarks: ${e.message}")
                emptyList()
            }
        }

        suspend fun getAllUserData(userId: String): FireUser? {
            return try {
                val querySnapshot = personCollectionRef
                    .whereEqualTo("userId", userId)
                    .get()
                    .await()

                val document = querySnapshot.documents.firstOrNull() ?: return null

                val listOfBookmarks = (document.get("listOfBookmarks") as? List<Map<String, Any?>>)
                    ?.map { Bookmark.fromFirestore(it) }

                val listOfNotes = (document.get("listOfNotes") as? List<Map<String, Any?>>)
                    ?.map { Note.fromFirestore(it) }

                val listOfTasks = (document.get("listOfTasks") as? List<Map<String, Any?>>)
                    ?.map { Task.fromFirestore(it) }



                FireUser(
                    userId = document.getString("userId"),
                    listOfTasks = listOfTasks,
                    listOfBookmarks = listOfBookmarks,
                    listOfNotes = listOfNotes,
                    settings = null
                )
            } catch (e: Exception) {
                println("Error fetching user data: ${e.message}")
                null
            }
        }}

