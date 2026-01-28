package ru.sochasapps.gvosynative.data.repositories

import ru.sochasapps.gvosynative.AppPreferences
import ru.sochasapps.gvosynative.data.api.MessageApiService
import ru.sochasapps.gvosynative.data.dto.MessageDto
import java.io.File

class VoiceMessageRepositoryImpl(
    private val s3Repository: S3Repository,
    private val messageApiService: MessageApiService,
    private val userRepository: UserRepository,
    private val appPreferences: AppPreferences
) : VoiceMessageRepository {

    override suspend fun uploadAudioToStorage(audioFile: File): Result<String> {
        return s3Repository.uploadAudioFile(audioFile)
    }

    override suspend fun sendAudioMetadataToServer(audioUrl: String): Result<String> {
        return try {

            val conversationId = appPreferences.getOrCreateConversationId()
            val user = userRepository.getCurrentUser() ?: return Result.failure(
                IllegalStateException("User not authenticated")
            )

            val response = messageApiService.sendVoiceMessage(
                token = user.userToken,
                audioUrl = audioUrl,
                conversationId = conversationId
            )

            Result.success(response.id)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    suspend fun sendTextMessage(text: String): Result<String> {
        return try {
            val user = userRepository.getCurrentUser() ?: return Result.failure(
                IllegalStateException("User not authenticated")
            )
            val conversationId = appPreferences.getOrCreateConversationId()
            val response = messageApiService.sendTextMessage(
                token = user.userToken,
                text = text,
                conversationId = conversationId
            )
            Result.success(response.id)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getConversation(conversationId: String): Result<List<MessageDto>> {
        return try {
            val user = userRepository.getCurrentUser() ?: return Result.failure(
                IllegalStateException("User not authenticated")
            )
            val messages = messageApiService.getConversation(
                token = user.userToken,
                conversationId = conversationId
            )
            Result.success(messages)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}