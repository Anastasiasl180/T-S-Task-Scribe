package com.aopr.authentication_data.impl

import android.content.Context
import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import com.aopr.authentication_domain.interactors.AuthenticationRepository
import com.aopr.firebase_domain.firestore_user_data.FireUser
import com.aopr.shared_data.proto_data_store.UserDataDataStoreManager
import com.aopr.shared_domain.inter.UserData
import com.aopr.shared_domain.inter.UserDataForFireBase
import com.aopr.shared_domain.inter.mapToBase64FromUri
import com.aopr.shared_domain.inter.mapToBitmapFromString
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import org.koin.core.annotation.Single

@Single
class AuthenticationRepositoryImpl(
    private val dataStoreManager: UserDataDataStoreManager
) : AuthenticationRepository {

    private lateinit var auth: FirebaseAuth
    private val personCollectionRef = Firebase.firestore.collection("users")
    override suspend fun registerUser(gmail: String, password: String): String {
        val auth = FirebaseAuth.getInstance()

        return if (gmail.isNotEmpty() && password.isNotEmpty()) {
            try {
                auth.createUserWithEmailAndPassword(gmail, password).await()
                val uid = auth.currentUser?.uid ?: throw Exception("User ID is null")
                uid
            } catch (e: Exception) {
                ""
            }
        } else {
            ""
        }
    }


    override suspend fun logInUser(gmail: String, password: String): String? {
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

    override suspend fun sendResetPasswordCode(gmail: String) {
        val auth = FirebaseAuth.getInstance()
        auth.sendPasswordResetEmail(gmail)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    println("Reset email sent")

                } else {
                    println("error")
                }

            }
    }

    override suspend fun saveUserDataIntoDB(userDataForFireBase: UserDataForFireBase) {
        dataStoreManager.updateUserData(userDataForFireBase)
    }

    @RequiresApi(Build.VERSION_CODES.P)
    override suspend fun setNameAndPictureInDBAndFire(
        uri: Uri,
        context: Context
    ): UserDataForFireBase {
        val userDataFprFireBase = UserData().mapToBase64FromUri(uri, context)
        dataStoreManager.updateUserData(userDataFprFireBase)
        return UserData().mapToBase64FromUri(uri, context)
    }


}