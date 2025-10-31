// repository/AvatarDao.kt

package com.example.gratio.repository

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.gratio.model.Avatar

@Dao
interface AvatarDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(avatar: Avatar)

    @Query("SELECT * FROM avatars WHERE is_default = 1 LIMIT 1")
    suspend fun getDefaultAvatar(): Avatar?

    @Query("SELECT * FROM avatars")
    suspend fun getAllAvatars(): List<Avatar>
}