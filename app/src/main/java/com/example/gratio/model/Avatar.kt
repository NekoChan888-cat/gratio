// app/src/main/java/com/example/gratio/model/Avatar.kt
package com.example.gratio.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "avatars")
data class Avatar(
    @PrimaryKey val avatar_id: Long,
    val name: String,
    val image_url: String,
    val rarity: String = "common",
    val unlock_condition: String? = null,
    val price_coins: Int = 0,
    val is_default: Int = 0
)