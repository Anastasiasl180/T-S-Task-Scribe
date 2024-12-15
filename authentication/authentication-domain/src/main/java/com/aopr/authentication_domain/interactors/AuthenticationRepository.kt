package com.aopr.authentication_domain.interactors

import com.aopr.authentication_domain.fier_store_uxer_data.FireUser

interface AuthenticationRepository {
    suspend fun registerUser(gmail: String, password: String)
    suspend fun logInUser(gmail: String, password: String)
    suspend fun saveFireUser(user: FireUser)
}