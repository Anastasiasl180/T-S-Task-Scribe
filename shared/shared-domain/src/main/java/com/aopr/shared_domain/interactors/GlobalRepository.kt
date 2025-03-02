package com.aopr.shared_domain.interactors

import com.aopr.shared_domain.colors_for_theme.Themes


interface GlobalRepository {
    suspend fun updateTheme(theme: Themes)
    suspend fun getTheme(): SettingsDto
    suspend fun updateIsFirstLaunch(isFirstLaunch: Boolean)
    suspend fun getIsFirstLaunch(): SettingsDto

}