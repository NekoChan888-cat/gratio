// repository/MessageTemplateDao.kt

package com.example.gratio.repository

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.gratio.model.MessageTemplate

@Dao
interface MessageTemplateDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(template: MessageTemplate)

    @Query("SELECT * FROM message_templates WHERE category = :category AND language_code = :languageCode AND is_global = 1 ORDER BY RANDOM() LIMIT 1")
    suspend fun getRandomTemplate(category: String, languageCode: String): MessageTemplate?

    @Query("SELECT * FROM message_templates WHERE language_code = :languageCode")
    suspend fun getAllTemplates(languageCode: String): List<MessageTemplate>
}