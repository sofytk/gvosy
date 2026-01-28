package ru.sochasapps.gvosynative.data.dto

import kotlinx.serialization.Serializable
import java.util.UUID
import kotlin.time.Instant

@Serializable
data class MessageDto(
    val id: String,
    val userId: String,
    val conversationId: String,
    val role: MessageRole,
    val content: String,
    val audioUrl: String? = null,
    val status: MessageStatus,
    val assistantId: String,
    val noteId: Long? = null,
    val createdAt: Instant
)

@Serializable
enum class MessageRole {
    USER, ASSISTANT
}

@Serializable
enum class MessageStatus {
    PENDING, COMPLETED, ERROR
}

