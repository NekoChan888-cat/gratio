// repository/TaskDao.kt

package com.example.gratio.repository

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.gratio.model.Task

@Dao
interface TaskDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(task: Task)

    @Query("SELECT * FROM tasks WHERE category = :category ORDER BY created_at DESC LIMIT 1")
    suspend fun getRandomTask(category: String): Task?

    @Query("SELECT * FROM tasks")
    suspend fun getAllTasks(): List<Task>

    @Query("SELECT * FROM tasks WHERE task_id = :taskId")
    suspend fun getTaskById(taskId: String): Task?}