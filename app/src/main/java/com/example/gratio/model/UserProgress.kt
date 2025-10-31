package com.example.gratio.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_progress")
data class UserProgress(
    @PrimaryKey val user_id: String,
    var total_tasks_completed: Int = 0,
    var streak_days: Int = 0,
    var last_task_date: Long = 0L,
    var points: Int = 0 // это и есть кристаллики!
)