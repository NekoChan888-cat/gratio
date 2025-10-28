// app/src/main/java/com/example/gratio/model/Task.kt
package com.example.gratio.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "tasks")
data class Task(
    @PrimaryKey val task_id: Long,
    val title: String,
    val description: String,
    val category: String,
    val points_reward: Int,
    val is_recurring: Int = 0,
    val created_at: Date = Date()
)