// app/src/main/java/com/example/gratio/model/UserSettings.kt
package com.example.gratio.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_settings")
data class UserSettings(
    @PrimaryKey val setting_id: Long = 0,
    val user_id: Long,
    val sound_enabled: Int = 1,
    val music_volume: Int = 50,
    val notifications_enabled: Int = 1,
    val theme: String = "light",
    val app_language: String = "ru"
)