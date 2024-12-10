package com.example.home_domain.interactors

import com.aopr.home.home_screen.proto_data_store.SettingsDto

interface HomeRepository {
    suspend fun updateTheme(theme: SettingsDto)

}