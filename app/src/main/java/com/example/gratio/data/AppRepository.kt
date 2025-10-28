// app/src/main/java/com/example/gratio/data/AppRepository.kt
package com.example.gratio.data

import android.content.Context
import com.example.gratio.model.*
import com.example.gratio.repository.*
import kotlinx.coroutines.flow.Flow

class AppRepository private constructor(
    private val userDao: UserDao,
    private val taskDao: TaskDao,
    private val completedTaskDao: CompletedTaskDao,
    private val messageTemplateDao: MessageTemplateDao
) {
    fun getFirstUser(): Flow<User?> = userDao.getFirstUser()
    fun getAllTasks(): Flow<List<Task>> = taskDao.getAllTasks()
    fun getCompliments(lang: String = "ru") = messageTemplateDao.getTemplatesByCategory("compliment", lang)

    suspend fun insertUser(user: User) = userDao.insertUser(user)
    suspend fun addCompletedTask(completedTask: CompletedTask) = completedTaskDao.insert(completedTask)

    companion object {
        @Volatile
        private var INSTANCE: AppRepository? = null

        fun getInstance(context: Context): AppRepository {
            return INSTANCE ?: synchronized(this) {
                val database = AppDatabase.getDatabase(context)
                AppRepository(
                    database.userDao(),
                    database.taskDao(),
                    database.completedTaskDao(),
                    database.messageTemplateDao()
                ).also { INSTANCE = it }
            }
        }
    }
}