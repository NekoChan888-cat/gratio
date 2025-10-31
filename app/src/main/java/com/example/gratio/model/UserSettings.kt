// model/UserSettings.kt

package com.example.gratio.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_settings")
data class UserSettings(
    @PrimaryKey val setting_id: String = "default", // Уникальный ID для настроек пользователя
    val user_id: String,
    val sound_enabled: Boolean = true,
    val music_volume: Int = 50,
    val notifications_enabled: Boolean = true,
    val theme: String = "light",
    val app_language: String = "ru"
)