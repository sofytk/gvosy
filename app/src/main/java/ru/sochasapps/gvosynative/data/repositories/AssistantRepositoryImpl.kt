package ru.sochasapps.gvosynative.data.repositories

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.sochasapps.gvosynative.data.api.AssistantApi
import ru.sochasapps.gvosynative.data.dao.AssistantDao
import ru.sochasapps.gvosynative.data.models.AssistantEntity
import ru.sochasapps.gvosynative.data.dto.AssistantRequest
import ru.sochasapps.gvosynative.data.dto.AssistantResponse

class AssistantRepositoryImpl(private val dao : AssistantDao, private val api : AssistantApi) :
    AssistantRepository {

    override suspend fun addAssistant(request: AssistantRequest): Result<AssistantEntity> = withContext(Dispatchers.IO) {
        return@withContext try{
            val response = api.add(request)
            println("AssistantRepositoryImpl: calling registerUser with ${request.assistantName}")
            val assistant = AssistantResponse(
                response.assistantName,
                assistantAge = response.assistantAge,
                assistantSex = response.assistantSex,
                assistantDescription = response.assistantDescription,
                assistantImg = response.assistantImg
            )
            dao.add(assistant.toEntity())
            Result.success(assistant.toEntity())
        } catch (e: Exception) {
            println("AssistantRepositoryImpl: error = ${e.message}")
            Result.failure(e)
        }

    }

    override suspend fun getAssistant(id: Long) : AssistantEntity? {
        val assistant : AssistantEntity? = dao.getAssistant()
        return assistant
    }
}