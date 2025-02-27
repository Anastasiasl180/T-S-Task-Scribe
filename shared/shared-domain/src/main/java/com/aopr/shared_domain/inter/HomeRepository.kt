package com.aopr.shared_domain.inter

import com.aopr.shared_domain.colors_for_theme.Themes
import kotlinx.coroutines.flow.Flow


interface HomeRepository {
    suspend fun updateTheme(theme: Themes)
    suspend fun getTheme(): SettingsDto
    suspend fun updateIsFirstLaunch(isFirstLaunch: Boolean)
    suspend fun getIsFirstLaunch(): SettingsDto

}