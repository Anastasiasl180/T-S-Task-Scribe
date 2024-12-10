package com.example.home_data.impl

import com.example.home_data.impl.proto_data_store.SettingsDataStoreManager
import com.example.home_data.impl.proto_data_store.SettingsDto
import com.example.home_domain.interactors.HomeRepository
import org.koin.core.annotation.Single

@Single
class HomeRepositoryImpl (private val dataStoreManager: SettingsDataStoreManager): HomeRepository{
    override suspend fun updateTheme(theme: SettingsDto) {

    }

}