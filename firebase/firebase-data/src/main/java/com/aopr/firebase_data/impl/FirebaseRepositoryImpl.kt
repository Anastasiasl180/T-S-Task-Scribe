package com.aopr.firebase_data.impl

import com.aopr.notes_domain.models.Note
import com.aopr.shared_domain.inter.SettingsDto
import com.aopr.tasks_domain.models.Task
import com.example.bookmarks_domain.models.Bookmark
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

/*
@Single
class FirebaseRepositoryImpl:FirebaseRepository {

    private lateinit var auth: FirebaseAuth
    private val personCollectionRef = Firebase.firestore.collection("users")
    override suspend fun updateUserDataBookmark(id: String, updates: Map<String, List<Bookmark>>) {
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

    override suspend fun updateUserDataTask(id: String, updates: Map<String, List<Task>>) {
        TODO("Not yet implemented")
    }

    override suspend fun updateUserDataNote(id: String, updates: Map<String, List<Note>>) {
        TODO("Not yet implemented")
    }

    override suspend fun updateUserDataSettingsDto(
        id: String,
        updates: Map<String, List<SettingsDto>>
    ) {
        TODO("Not yet implemented")
    }
}*/
object FirebaseHelper {
    private lateinit var auth: FirebaseAuth
    private val personCollectionRef = Firebase.firestore.collection("users")

    suspend fun updateUserDataBookmark(id: String?, updates: Map<String, List<Map<String, Any?>>>) {
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
}
