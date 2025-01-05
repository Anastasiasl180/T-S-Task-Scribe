package com.aopr.authentication_domain.interactors

import com.aopr.firebase_domain.firestore_user_data.FireUser
import kotlinx.coroutines.flow.Flow

interface AuthenticationRepository {
    suspend fun registerUser(gmail: String, password: String):String
    suspend fun logInUser(gmail: String, password: String):String?
    suspend fun saveFireUser(user:FireUser):String?
    suspend fun retrieveUser(id:String): Flow<FireUser?>
}