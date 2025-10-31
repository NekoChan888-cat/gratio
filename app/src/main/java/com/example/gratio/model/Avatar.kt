// model/Avatar.kt

package com.example.gratio.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "avatars")
data class Avatar(
    @PrimaryKey val avatar_id: String,
    val name: String,
    val image_url: String, // URL или путь к ресурсу
    val rarity: String = "common", // common, rare, epic, legendary
    val unlock_condition: String? = null,
    val price_coins: Int = 0,
    val is_default: Boolean = false
)