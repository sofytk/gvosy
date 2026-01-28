package ru.sochasapps.gvosynative.data.models

data class AssistantAction(
    val type: ActionType,
    val label: String,
    val targetId: String? = null
)

enum class ActionType {
    NOTE, TODO_LIST, REMINDER
}