// app/src/main/java/com/example/gratio/model/CompletedTask.kt
package com.example.gratio.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.gratio.model.Task
import com.example.gratio.model.User
import java.util.Date

@Entity(
    tableName = "completed_tasks",
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["user_id"],
            childColumns = ["user_id"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Task::class,
            parentColumns = ["task_id"],
            childColumns = ["task_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class CompletedTask(
    @PrimaryKey(autoGenerate = true) val completion_id: Long = 0,
    val user_id: Long,
    val task_id: Long,
    val completed_at: Date = Date(),
    val is_confirmed: Int = 0
)