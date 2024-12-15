package com.aopr.authentication_data.impl

import android.widget.Toast
import com.aopr.authentication_domain.fier_store_uxer_data.FireUser
import com.aopr.authentication_domain.interactors.AuthenticationRepository
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
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

    override suspend fun logInUser(gmail: String, password: String) {
        val auth = FirebaseAuth.getInstance()
        if (gmail.isNotEmpty() && password.isNotEmpty()) {

            CoroutineScope(Dispatchers.IO).launch {
                try {
                    auth.signInWithEmailAndPassword(gmail, password).await()

                    checkLoggedInState()

                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        println(e.message.toString())
                    }
                }
            }

        }
    }

    private fun checkLoggedInState() {
        if (auth.currentUser == null) {
            println("nulik")
        } else {
            println("no null")
        }
    }

    override suspend fun saveFireUser(user: FireUser) {
        try {
            personCollectionRef.add(user).await()
            println("Success")
        } catch (e: Exception) {
            println("NoSuccess")
        }
    }
}