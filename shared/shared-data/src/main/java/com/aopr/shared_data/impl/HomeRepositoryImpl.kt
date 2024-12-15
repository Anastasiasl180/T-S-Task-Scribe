package com.aopr.shared_data.impl

import android.util.Log
import com.aopr.shared_data.proto_data_store.SettingsDataStoreManager
import com.aopr.shared_domain.colors_for_theme.Themes
import com.aopr.shared_domain.inter.HomeRepository
import com.aopr.shared_domain.inter.SettingsDto
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import org.koin.core.annotation.Single

@Single
class HomeRepositoryImpl(
    private val dataStoreManager: SettingsDataStoreManager
) : HomeRepository {
    private lateinit var auth: FirebaseAuth
    override suspend fun updateTheme(theme: Themes) {
        dataStoreManager.updateTheme(theme)
    }

    override suspend fun getTheme(): SettingsDto {
        return dataStoreManager.data.first()
    }

    override suspend fun updateIsFirstLaunch(isFirstLaunch: Boolean) {
        dataStoreManager.updateIsFirstLaunch(isFirstLaunch)
    }

    override suspend fun getIsFirstLaunch(): SettingsDto {
        return dataStoreManager.data.first()
    }

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

    private fun checkLoggedInState() {
        if (auth.currentUser == null) {
            println("nulik")
        } else {
            println("no null")
        }
    }
}