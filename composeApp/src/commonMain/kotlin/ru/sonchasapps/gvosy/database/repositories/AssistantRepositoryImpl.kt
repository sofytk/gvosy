package ru.sonchasapps.gvosy.database.repositories

import ru.sonchasapps.gvosy.database.dao.AssistantDao
import ru.sonchasapps.gvosy.database.entities.AssistantEntity

class AssistantRepositoryImpl(private val dao : AssistantDao) : AssistantRepository {

    override suspend fun insertAssistant(assistantEntity: AssistantEntity) {
        dao.insert(assistantEntity)
    }

    override suspend fun deleteAssistant(assistantEntity: AssistantEntity) {
        dao.delete(assistantEntity)
    }

    override suspend fun updateAssistant(assistantEntity: AssistantEntity) {
        dao.update(assistantEntity)
    }

    override suspend fun getAssistant(id: Long) : AssistantEntity? {
        val assistant : AssistantEntity? = dao.getAssistant(id)
        return assistant
    }
}