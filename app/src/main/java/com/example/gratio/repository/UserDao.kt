// app/src/main/java/com/example/gratio/repository/UserDao.kt
package com.example.gratio.repository

import androidx.room.*
import com.example.gratio.model.User
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Query("SELECT * FROM users WHERE user_id = :id")
    fun getUserById(id: Long): Flow<User>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: User): Long

    @Update
    suspend fun updateUser(user: User)

    @Query("SELECT * FROM users LIMIT 1")
    fun getFirstUser(): Flow<User?>
}