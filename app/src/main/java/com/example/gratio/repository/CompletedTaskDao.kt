// repository/CompletedTaskDao.kt

package com.example.gratio.repository

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.gratio.model.CompletedTask

@Dao
interface CompletedTaskDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(completedTask: CompletedTask)

    @Query("SELECT COUNT(*) FROM completed_tasks WHERE user_id = :userId AND completed_at >= :startDate")
    suspend fun getTasksCompletedToday(userId: String, startDate: Long): Int

    @Query("SELECT * FROM completed_tasks WHERE user_id = :userId ORDER BY completed_at DESC LIMIT 10")
    suspend fun getRecentCompletedTasks(userId: String): List<CompletedTask>
}