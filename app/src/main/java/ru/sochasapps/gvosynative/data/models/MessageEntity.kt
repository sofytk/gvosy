package ru.sochasapps.gvosynative.data.models

sealed class MessageEntity {
    abstract val id: String
    abstract val timestamp: Long

    data class UserText(
        override val id: String,
        val text: String,
        override val timestamp: Long = System.currentTimeMillis()
    ) : MessageEntity()

    data class UserVoice(
        override val id: String,
        val audioUrl: String,
        val durationSec: Int,
        val transcription: String? = null,
        override val timestamp: Long = System.currentTimeMillis()
    ) : MessageEntity()

    data class AssistantResponse(
        override val id: String,
        val text: String,
        val action: AssistantAction? = null,
        override val timestamp: Long = System.currentTimeMillis()
    ) : MessageEntity()
}

