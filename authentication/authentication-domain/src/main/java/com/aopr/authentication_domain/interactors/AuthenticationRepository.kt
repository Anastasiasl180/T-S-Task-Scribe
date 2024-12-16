package com.aopr.authentication_domain.interactors

import com.aopr.firebase_domain.fier_store_uxer_data.FireUser
import kotlinx.coroutines.flow.Flow

interface AuthenticationRepository {
    suspend fun registerUser(gmail: String, password: String)
    suspend fun logInUser(gmail: String, password: String)
    suspend fun saveFireUser(user: com.aopr.firebase_domain.fier_store_uxer_data.FireUser):String?
    suspend fun retrieveUser(id:String): Flow<com.aopr.firebase_domain.fier_store_uxer_data.FireUser?>
}