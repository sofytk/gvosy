package ru.sonchasapps.gvosy.data.models

sealed interface MessageEntity {
    val id: String
    val timestamp: Long

    data class UserText(
        override val id: String,
        override val timestamp: Long,
        val text: String
    ) : MessageEntity

    data class UserVoice(
        override val id: String,
        override val timestamp: Long,
        val durationSec: Int,
        val audioUrl: String
    ) : MessageEntity

    data class AssistantResponse(
        override val id: String,
        override val timestamp: Long,
        val text: String,
        val action: AssistantAction? = null
    ) : MessageEntity
}


data class AssistantAction(
    val label: String,
    val targetId: String,
    val type: ActionType
)

enum class ActionType {
    NOTE, TODO_LIST, JOURNAL, WEEKLY_PLAN,
    PROJECT_IDEA,
    TASK,
    OTHER
}
