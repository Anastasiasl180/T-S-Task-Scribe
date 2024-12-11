package com.aopr.shared_data.impl

import com.aopr.shared_data.proto_data_store.SettingsDataStoreManager
import com.aopr.shared_domain.colors_for_theme.Themes
import com.aopr.shared_domain.inter.HomeRepository
import com.aopr.shared_domain.inter.SettingsDto
import kotlinx.coroutines.flow.first
import org.koin.core.annotation.Single

@Single
class HomeRepositoryImpl(private val dataStoreManager: SettingsDataStoreManager) : HomeRepository {
    override suspend fun updateTheme(theme:Themes) {
        dataStoreManager.updateTheme(theme)
    }

    override suspend fun getTheme(): SettingsDto {
        return dataStoreManager.data.first()
    }

}