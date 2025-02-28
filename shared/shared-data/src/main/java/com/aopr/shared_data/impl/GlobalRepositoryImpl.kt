package com.aopr.shared_data.impl

import com.aopr.shared_data.proto_data_store.SettingsDataStoreManager
import com.aopr.shared_domain.colors_for_theme.Themes
import com.aopr.shared_domain.inter.GlobalRepository
import com.aopr.shared_domain.inter.SettingsDto
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.flow.first
import org.koin.core.annotation.Single

@Single
class GlobalRepositoryImpl(
    private val dataStoreManager: SettingsDataStoreManager
) : GlobalRepository {
    private lateinit var auth: FirebaseAuth
    private val personCollectionRef = Firebase.firestore.collection("users")

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



}