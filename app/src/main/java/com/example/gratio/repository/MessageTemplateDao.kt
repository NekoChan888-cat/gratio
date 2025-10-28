// app/src/main/java/com/example/gratio/repository/MessageTemplateDao.kt
package com.example.gratio.repository

import androidx.room.*
import com.example.gratio.model.MessageTemplate
import kotlinx.coroutines.flow.Flow

@Dao
interface MessageTemplateDao {
    @Query("SELECT * FROM message_templates WHERE category = :category AND language_code = :lang")
    fun getTemplatesByCategory(category: String, lang: String = "ru"): Flow<List<MessageTemplate>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTemplates(templates: List<MessageTemplate>)
}