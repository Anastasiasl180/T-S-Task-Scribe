package com.aopr.firebase_data.helpers

import com.aopr.firebase_domain.firestore_user_data.FireUser
import com.aopr.notes_domain.interactors.NotesUseCase
import com.aopr.notes_domain.models.Note
import com.aopr.notes_domain.models.noteFromFirestore
import com.aopr.shared_domain.interactors.SettingsDto
import com.aopr.tasks_domain.interactors.TasksUseCase
import com.aopr.tasks_domain.mappers.taskFromFirestore
import com.aopr.tasks_domain.models.Task
import com.example.bookmarks_domain.interactors.BookmarksUseCase
import com.example.bookmarks_domain.models.Bookmark
import com.example.bookmarks_domain.models.bookmarkFromFirestore
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await


class Helpers {


    object FirebaseHelper {
        private lateinit var auth: FirebaseAuth

      //  private val personCollectionRef = Firebase.firestore.collection("users")

        suspend fun updateUserDataBookmark(
            id: String?,
            updates: Map<String, List<Map<String, Any?>>>
        ) {
            try {
                val personCollectionRef = FirebaseFirestore.getInstance().collection("users")
                val querySnapshot = personCollectionRef.whereEqualTo("userId", id)
                    .get()
                    .await()
                val document = querySnapshot.documents.firstOrNull()

                if (document != null) {
                    personCollectionRef.document(document.id).update(updates).await()
                }
            } catch (e: Exception) {

            }
        }

        suspend fun updateUserDataTask(id: String, updates: Map<String, List<Task>>) {
            try {
                val personCollectionRef = FirebaseFirestore.getInstance().collection("users")
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
                val personCollectionRef = FirebaseFirestore.getInstance().collection("users")
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
                val personCollectionRef = FirebaseFirestore.getInstance().collection("users")
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
                val personCollectionRef = FirebaseFirestore.getInstance().collection("users")
                val querySnapshot = personCollectionRef.whereEqualTo("userId", userId)
                    .get()
                    .await()
                val document = querySnapshot.documents.firstOrNull()

                if (document != null) {
                    val bookmarks = document.get("bookmarks") as? List<Map<String, Any?>>
                    bookmarks?.map { bookmarkFromFirestore(it) } ?: emptyList()
                } else {
                    emptyList()
                }
            } catch (e: Exception) {
                println("Error fetching bookmarks: ${e.message}")
                emptyList()
            }
        }

        suspend fun getAllUserData(
            userId: String,
            bookmarksUseCase: BookmarksUseCase,
            tasksUseCase: TasksUseCase,
            notesUseCase: NotesUseCase
        ): FireUser? {
            val personCollectionRef = FirebaseFirestore.getInstance().collection("users")

            val scope = CoroutineScope(currentCoroutineContext())
            return try {
                val querySnapshot = personCollectionRef
                    .whereEqualTo("userId", userId)
                    .get()
                    .await()
                val document = querySnapshot.documents.firstOrNull() ?: return null


                val picture = document.getString("userPicture")

                val name = document.getString("userName")

                val listOfBookmarks = (document.get("listOfBookmarks") as? List<Map<String, Any?>>)
                    ?.map { bookmarkFromFirestore(it) }
                scope.launch(NonCancellable) {
                    bookmarksUseCase.setBookmarksFromFire(listOfBookmarks).launchIn(scope)
                }
                val listOfNotes = (document.get("listOfNotes") as? List<Map<String, Any?>>)
                    ?.map { noteFromFirestore(it) }

                notesUseCase.setNotesFromFire(listOfNotes)

                val listOfTasks = (document.get("listOfTasks") as? List<Map<String, Any?>>)
                    ?.map { taskFromFirestore(it) }

                tasksUseCase.setTasksFromFire(listOfTasks)
                FireUser(
                    userId = document.getString("userId"),
                    listOfTasks = listOfTasks,
                    listOfBookmarks = listOfBookmarks,
                    listOfNotes = listOfNotes,
                    userPicture = picture,
                    userName =name
                )

            } catch (e: Exception) {
                println("Error fetching user data: ${e.message}")
                null
            }
        }


    }
}

