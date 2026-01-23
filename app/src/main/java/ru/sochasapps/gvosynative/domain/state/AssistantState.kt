package ru.sochasapps.gvosynative.domain.state

import ru.sochasapps.gvosynative.data.models.AssistantEntity


sealed class AssistantState {
    data object Idle : AssistantState()
    data object Loading : AssistantState()
    data class Success(val assistant: AssistantEntity? = null) : AssistantState()
    data class Error(val message: String?) : AssistantState()
}