package com.example.gratio.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tasks")
data class Task(
    @PrimaryKey val task_id: String,
    val title: String,
    val description: String,
    val category: String, // daily, weekly, one_time
    val points_reward: Int, // количество кристалликов
    val is_recurring: Boolean = false,
    val created_at: Long = System.currentTimeMillis()
)