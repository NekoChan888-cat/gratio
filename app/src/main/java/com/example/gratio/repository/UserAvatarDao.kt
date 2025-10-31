// repository/UserAvatarDao.kt

package com.example.gratio.repository

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.gratio.model.UserAvatar

@Dao
interface UserAvatarDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(userAvatar: UserAvatar)

    @Query("SELECT * FROM user_avatars WHERE user_id = :userId AND is_equipped = 1")
    suspend fun getEquippedAvatar(userId: String): UserAvatar?

    @Query("SELECT * FROM user_avatars WHERE user_id = :userId")
    suspend fun getAllUserAvatars(userId: String): List<UserAvatar>

    @Query("UPDATE user_avatars SET is_equipped = 0 WHERE user_id = :userId")
    suspend fun unequipAllAvatars(userId: String)

    @Query("UPDATE user_avatars SET is_equipped = 1 WHERE user_avatar_id = :userAvatarId")
    suspend fun equipAvatar(userAvatarId: Long)
}