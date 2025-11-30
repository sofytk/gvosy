package ru.sonchasapps.gvosy.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import ru.sonchasapps.gvosy.data.models.AssistantEntity

@Dao
interface AssistantDao {
    @Insert()
    suspend fun add(assistantEntity: AssistantEntity)

    @Delete
    suspend fun delete(assistantEntity: AssistantEntity)

    @Query("SELECT * from assistant_data")
    fun getAssistant(): AssistantEntity?
}