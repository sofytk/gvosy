package ru.sochasapps.gvosynative.domain.state

import ru.sochasapps.gvosynative.data.models.UserEntity

sealed class AuthUiState {
    data object Idle : AuthUiState()
    data object Loading : AuthUiState()
    data class Success(val user: UserEntity? = null) : AuthUiState()
    data class Error(val message: String?) : AuthUiState()
}