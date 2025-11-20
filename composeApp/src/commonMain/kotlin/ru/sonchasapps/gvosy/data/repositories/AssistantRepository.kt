package ru.sonchasapps.gvosy.data.repositories

import ru.sonchasapps.gvosy.data.models.AssistantEntity

interface AssistantRepository {
    suspend fun insertAssistant(assistantEntity: AssistantEntity)
    suspend fun deleteAssistant(assistantEntity: AssistantEntity)
    suspend fun updateAssistant(assistantEntity: AssistantEntity)
    suspend fun getAssistant(id: Long) : AssistantEntity?
}