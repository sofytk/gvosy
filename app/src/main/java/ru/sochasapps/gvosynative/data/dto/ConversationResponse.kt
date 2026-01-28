package ru.sochasapps.gvosynative.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class ConversationResponse(
    val messages: List<MessageDto>,
    val conversationId: String
)
