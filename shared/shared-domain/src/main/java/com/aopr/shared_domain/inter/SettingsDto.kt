package com.aopr.shared_domain.inter

import com.aopr.shared_domain.colors_for_theme.Themes
import kotlinx.serialization.Serializable

@Serializable
data class SettingsDto(
    val theme: Themes = Themes.VIOLET
)