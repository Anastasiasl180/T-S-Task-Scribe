package com.aopr.authentication_data.impl

import com.aopr.authentication_domain.interactors.AuthenticationRepository
import com.aopr.firebase_domain.firestore_user_data.FireUser
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import org.koin.core.annotation.Single

@Single
class AuthenticationRepositoryImpl : AuthenticationRepository {

    private lateinit var auth: FirebaseAuth
    private val personCollectionRef = Firebase.firestore.collection("users")
    override suspend fun registerUser(gmail: String, password: String) {
        val auth = FirebaseAuth.getInstance()
        if (gmail.isNotEmpty() && password.isNotEmpty()) {

            CoroutineScope(Dispatchers.IO).launch {
                try {
                    auth.createUserWithEmailAndPassword(gmail, password).await()
                    checkLoggedInState()

                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        println(e.message.toString())
                    }
                }
            }

        }
    }

    override suspend fun logInUser(gmail: String, password: String):String? {
        val auth = FirebaseAuth.getInstance()
        return if (gmail.isNotEmpty() && password.isNotEmpty()) {
            try {
               auth.signInWithEmailAndPassword(gmail, password).await()

               val user = auth.currentUser
                user?.uid
            } catch (e: Exception) {
                println("Login error: ${e.message}")
                null
            }
        } else {
            null
        }

    }

    private fun checkLoggedInState() {
        if (auth.currentUser == null) {

        } else {
            println("no null")
        }
    }

    override suspend fun saveFireUser(user: FireUser): String? {
        return try {
            val document = personCollectionRef.add(user).await()
            println("Success")
            document.id
        } catch (e: Exception) {
            println("NoSuccess: ${e.message}")
            null
        }
    }

    override suspend fun retrieveUser(id: String): Flow<FireUser> {
        return flow {
            try {
                val querySnapshot = personCollectionRef
                    .whereEqualTo("userId", id)
                    .get()
                    .await()
                val document = querySnapshot.documents.firstOrNull()
                document?.toObject(FireUser::class.java)
            } catch (e: Exception) {

            }
        }
    }


}