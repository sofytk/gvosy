package ru.sochasapps.gvosynative.data.repositories

import ru.sochasapps.gvosynative.data.models.AssistantEntity
import ru.sochasapps.gvosynative.data.dto.AssistantRequest

interface AssistantRepository {
    suspend fun addAssistant(assistantRequest: AssistantRequest) : Result<AssistantEntity>
    suspend fun getAssistant(id: Long) : AssistantEntity?
}