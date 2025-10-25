package ru.sonchasapps.gvosy.database.repositories

import ru.sonchasapps.gvosy.database.entities.AssistantEntity
import ru.sonchasapps.gvosy.database.entities.UserEntity

interface AssistantRepository {
    suspend fun insertAssistant(assistantEntity: AssistantEntity)
    suspend fun deleteAssistant(assistantEntity: AssistantEntity)
    suspend fun updateAssistant(assistantEntity: AssistantEntity)
    suspend fun getAssistant(id: Long) : AssistantEntity?
}