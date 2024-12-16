package com.aopr.shared_domain.inter

import com.aopr.shared_domain.colors_for_theme.Themes
import kotlinx.serialization.Serializable

@Serializable
data class SettingsDto(
    val theme: Themes = Themes.VIOLET,
    val isFirstLaunch: Boolean = true
){
    fun toFirestore(): Map<String, Any?> {
        return mapOf(
            "theme" to theme.name,
            "isFirstLaunch" to isFirstLaunch
        )
    }

    companion object {
        fun fromFirestore(data: Map<String, Any?>): SettingsDto {
            return SettingsDto(
                theme = Themes.valueOf(data["theme"] as? String ?: "VIOLET"),
                isFirstLaunch = data["isFirstLaunch"] as? Boolean ?: true
            )
        }
    }
}