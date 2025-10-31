// repository/UserDao.kt

package com.example.gratio.repository

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.gratio.model.User

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: User)

    @Query("SELECT * FROM users WHERE user_id = :userId")
    suspend fun getUserById(userId: String): User?

    @Query("SELECT * FROM users WHERE email = :email AND is_guest = 0")
    suspend fun getUserByEmail(email: String): User?

    @Query("SELECT * FROM users WHERE guest_token = :token AND is_guest = 1")
    suspend fun getGuestByToken(token: String): User?
}