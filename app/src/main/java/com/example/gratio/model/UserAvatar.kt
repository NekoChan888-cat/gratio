// app/src/main/java/com/example/gratio/model/UserAvatar.kt
package com.example.gratio.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.Date

@Entity(
    tableName = "user_avatars",
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["user_id"],
            childColumns = ["user_id"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Avatar::class,
            parentColumns = ["avatar_id"],
            childColumns = ["avatar_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class UserAvatar(
    @PrimaryKey(autoGenerate = true) val user_avatar_id: Long = 0,
    val user_id: Long,
    val avatar_id: Long,
    val acquired_at: Date = Date(),
    val is_equipped: Int = 0
)