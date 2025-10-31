// model/User.kt

package com.example.gratio.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @PrimaryKey val user_id: String,
    val email: String,
    val guest_token: String? = null,
    val is_guest: Boolean = false,
    val created_at: Long = System.currentTimeMillis(),
    val last_login: Long = System.currentTimeMillis(),
    val language_code: String = "ru",
    val timezone: String = "UTC",
    val is_active: Boolean = true
)