package ru.sonchasapps.gvosy.domain

import ru.sonchasapps.gvosy.data.models.UserEntity

sealed class AuthUiState {
    data object Idle : AuthUiState()
    data object Loading : AuthUiState()
    data class Success(val user: UserEntity? = null) : AuthUiState()
    data class Error(val message: String?) : AuthUiState()
}
