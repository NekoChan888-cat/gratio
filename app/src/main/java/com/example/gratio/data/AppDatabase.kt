// app/src/main/java/com/example/gratio/data/AppDatabase.kt

package com.example.gratio.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.gratio.model.*
import com.example.gratio.repository.*
import java.util.Date

// --- Конвертер для Date ---
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

// --- Миграция с версии 1 на 2 ---
val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        // Создаем новые таблицы
        database.execSQL("""
            CREATE TABLE IF NOT EXISTS `user_progress` (
                `user_id` TEXT NOT NULL,
                `total_tasks_completed` INTEGER NOT NULL,
                `streak_days` INTEGER NOT NULL,
                `last_task_date` INTEGER NOT NULL,
                `points` INTEGER NOT NULL,
                PRIMARY KEY(`user_id`)
            )
        """.trimIndent())

        database.execSQL("""
            CREATE TABLE IF NOT EXISTS `user_settings` (
                `setting_id` TEXT NOT NULL,
                `user_id` TEXT NOT NULL,
                `sound_enabled` INTEGER NOT NULL,
                `music_volume` INTEGER NOT NULL,
                `notifications_enabled` INTEGER NOT NULL,
                `theme` TEXT NOT NULL,
                `app_language` TEXT NOT NULL,
                PRIMARY KEY(`setting_id`)
            )
        """.trimIndent())

        // Добавьте создание остальных новых таблиц, если они не существовали в версии 1
        // Например: avatar, user_avatar, completed_task
    }
}

// --- Основной класс базы данных ---
@Database(
    entities = [
        User::class,
        UserProgress::class,
        UserSettings::class,
        Task::class,
        MessageTemplate::class,
        Avatar::class,
        UserAvatar::class,
        CompletedTask::class
    ],
    version = 2,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun userProgressDao(): UserProgressDao
    abstract fun userSettingsDao(): UserSettingsDao
    abstract fun taskDao(): TaskDao
    abstract fun messageTemplateDao(): MessageTemplateDao
    abstract fun avatarDao(): AvatarDao
    abstract fun userAvatarDao(): UserAvatarDao
    abstract fun completedTaskDao(): CompletedTaskDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "gratio_database"
                )
                    // --- ВЫБЕРИТЕ ОДИН ИЗ ВАРИАНТОВ ---

                    // Вариант 1: Для разработки (удаляет данные при изменении схемы)
                    .fallbackToDestructiveMigration()

                    // Вариант 2: Для продакшена (сохраняет данные)
                    // .addMigrations(MIGRATION_1_2)

                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}