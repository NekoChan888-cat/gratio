// app/src/main/java/com/example/gratio/data/AppDatabase.kt
package com.example.gratio.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.gratio.model.*
import com.example.gratio.repository.*
import java.util.Date

@Database(
    entities = [
        User::class,
        Task::class,
        CompletedTask::class,
        UserProgress::class,
        MessageTemplate::class,
        Avatar::class,
        UserAvatar::class,
        UserSettings::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun taskDao(): TaskDao
    abstract fun completedTaskDao(): CompletedTaskDao
    abstract fun messageTemplateDao(): MessageTemplateDao
    // Добавьте другие DAO по мере необходимости

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "gratio_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}

// Конвертер для Date
class Converters {
    @androidx.room.TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @androidx.room.TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }
}