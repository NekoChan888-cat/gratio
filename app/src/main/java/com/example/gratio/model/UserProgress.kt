// app/src/main/java/com/example/gratio/model/UserProgress.kt
package com.example.gratio.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "user_progress")
data class UserProgress(
    @PrimaryKey val progress_id: Long = 0,
    val user_id: Long,
    val total_tasks_completed: Int = 0,
    val streak_days: Int = 0,
    val last_task_date: Date? = null,
    val points: Int = 0
)