package ru.sochasapps.gvosynative.data.dto

import kotlinx.serialization.Serializable


@Serializable
data class TextMessageRequest(
    val text: String,
    val conversationId: String? = null
)
