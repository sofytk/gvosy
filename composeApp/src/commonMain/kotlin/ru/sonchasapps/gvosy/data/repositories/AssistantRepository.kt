package ru.sonchasapps.gvosy.data.repositories

import ru.sonchasapps.gvosy.data.models.AssistantEntity
import ru.sonchasapps.gvosy.data.models.AssistantRequest

interface AssistantRepository {
    suspend fun addAssistant(assistantRequest: AssistantRequest) : Result<AssistantEntity>
    suspend fun getAssistant(id: Long) : AssistantEntity?
}