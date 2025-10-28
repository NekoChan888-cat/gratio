// app/src/main/java/com/example/gratio/model/User.kt
package com.example.gratio.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true) val user_id: Long = 0,
    val email: String? = null,
    val guest_token: String? = null,
    val is_guest: Int = 1, // 1 - гость, 0 - зарегистрированный
    val created_at: Date = Date(),
    val last_login: Date? = null,
    val language_code: String? = null,
    val timezone: String? = null,
    val is_active: Int = 1
)