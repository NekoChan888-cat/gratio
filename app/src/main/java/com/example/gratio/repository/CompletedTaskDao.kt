// app/src/main/java/com/example/gratio/repository/CompletedTaskDao.kt
package com.example.gratio.repository

import androidx.room.*
import com.example.gratio.model.CompletedTask
import kotlinx.coroutines.flow.Flow
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query



@Dao
interface CompletedTaskDao {
    @Insert
    suspend fun insert(completedTask: CompletedTask)

    @Query("SELECT * FROM completed_tasks WHERE user_id = :userId")
    fun getCompletedTasksByUser(userId: Long): Flow<List<CompletedTask>>
}