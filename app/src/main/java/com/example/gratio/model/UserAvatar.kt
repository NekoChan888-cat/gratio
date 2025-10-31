package com.example.gratio.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_avatars")
data class UserAvatar(
    @PrimaryKey(autoGenerate = true) val user_avatar_id: Long? = null, // <-- ИЗМЕНЕНО
    val user_id: String,
    val avatar_id: String,
    val acquired_at: Long = System.currentTimeMillis(),
    val is_equipped: Boolean = false
)