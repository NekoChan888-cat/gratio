// app/src/main/java/com/example/gratio/model/MessageTemplate.kt
package com.example.gratio.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "message_templates")
data class MessageTemplate(
    @PrimaryKey val template_id: Long,
    val text: String,
    val category: String,
    val language_code: String = "ru",
    val is_global: Int = 1
)