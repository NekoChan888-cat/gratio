// model/CompletedTask.kt

package com.example.gratio.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "completed_tasks")
data class CompletedTask(
    @PrimaryKey(autoGenerate = true) val completion_id: Long,
    val user_id: String,
    val task_id: String,
    val completed_at: Long = System.currentTimeMillis(),
    val is_confirmed: Boolean = false
)