// model/MessageTemplate.kt

package com.example.gratio.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "message_templates")
data class MessageTemplate(
    @PrimaryKey val template_id: String,
    val text: String,
    val category: String, // например, "thank_you", "compliment"
    val language_code: String = "ru",
    val is_global: Boolean = true
)