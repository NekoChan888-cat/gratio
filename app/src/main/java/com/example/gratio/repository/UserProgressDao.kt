// repository/UserProgressDao.kt

package com.example.gratio.repository

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.gratio.model.UserProgress

@Dao
interface UserProgressDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(progress: UserProgress)

    @Query("SELECT * FROM user_progress WHERE user_id = :userId")
    suspend fun getProgress(userId: String): UserProgress?

    @Query("UPDATE user_progress SET points = points + :points, streak_days = :streakDays, last_task_date = :lastTaskDate WHERE user_id = :userId")
    suspend fun updateProgress(userId: String, points: Int, streakDays: Int, lastTaskDate: Long)
}