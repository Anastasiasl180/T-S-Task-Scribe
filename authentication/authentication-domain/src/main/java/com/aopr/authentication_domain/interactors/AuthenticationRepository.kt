package com.aopr.authentication_domain.interactors

import android.content.Context
import android.net.Uri
import com.aopr.firebase_domain.firestore_user_data.FireUser
import com.aopr.shared_domain.inter.UserData
import com.aopr.shared_domain.inter.UserDataForFireBase
import kotlinx.coroutines.flow.Flow

interface AuthenticationRepository {
    suspend fun registerUser(gmail: String, password: String):String
    suspend fun logInUser(gmail: String, password: String):String?
    suspend fun saveFireUser(user:FireUser):String?
    suspend fun retrieveUser(id:String): Flow<FireUser?>
    suspend fun sendResetPasswordCode(gmail: String)
    suspend fun setNameAndPictureInDBAndFire(uri: Uri,context: Context,name:String)
}