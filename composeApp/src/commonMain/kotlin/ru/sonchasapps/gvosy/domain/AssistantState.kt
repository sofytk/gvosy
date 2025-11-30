package ru.sonchasapps.gvosy.domain

import ru.sonchasapps.gvosy.data.models.AssistantEntity
import ru.sonchasapps.gvosy.data.models.UserEntity

sealed class AssistantState {
    data object Idle : AssistantState()
    data object Loading : AssistantState()
    data class Success(val assistant: AssistantEntity? = null) : AssistantState()
    data class Error(val message: String?) : AssistantState()
}