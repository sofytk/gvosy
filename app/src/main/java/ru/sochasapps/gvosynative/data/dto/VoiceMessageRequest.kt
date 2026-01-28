package ru.sochasapps.gvosynative.data.dto

import kotlinx.serialization.Serializable


@Serializable
data class VoiceMessageRequest(
    val audioUrl: String,
    val conversationId: String? = null
)
