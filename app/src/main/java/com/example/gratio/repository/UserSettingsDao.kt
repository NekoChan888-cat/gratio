// repository/UserSettingsDao.kt

package com.example.gratio.repository

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.gratio.model.UserSettings

@Dao
interface UserSettingsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(settings: UserSettings)

    @Query("SELECT * FROM user_settings WHERE user_id = :userId")
    suspend fun getSettings(userId: String): UserSettings?

    @Query("UPDATE user_settings SET sound_enabled = :soundEnabled, music_volume = :musicVolume, notifications_enabled = :notificationsEnabled, theme = :theme, app_language = :appLanguage WHERE user_id = :userId")
    suspend fun updateSettings(
        userId: String,
        soundEnabled: Boolean,
        musicVolume: Int,
        notificationsEnabled: Boolean,
        theme: String,
        appLanguage: String
    )
}