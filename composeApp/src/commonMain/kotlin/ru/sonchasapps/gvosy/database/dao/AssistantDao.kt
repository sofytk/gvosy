package ru.sonchasapps.gvosy.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow
import ru.sonchasapps.gvosy.database.entities.AssistantEntity
import ru.sonchasapps.gvosy.database.entities.UserEntity

@Dao
interface AssistantDao {
    @Insert()
    suspend fun insert(assistantEntity: AssistantEntity)

    @Update
    suspend fun update(assistantEntity: AssistantEntity)

    @Upsert
    suspend fun upsert(assistantEntity: AssistantEntity)

    @Delete
    suspend fun delete(assistantEntity: AssistantEntity)

    @Query("SELECT * from assistant_data WHERE id = :id")
    fun getAssistant(id: Long): AssistantEntity?
}