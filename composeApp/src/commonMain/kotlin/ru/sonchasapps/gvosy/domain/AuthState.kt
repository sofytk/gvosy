package ru.sonchasapps.gvosy.domain

sealed class AuthState {
    object Authorized: AuthState()
    object Loading: AuthState()
    object NotAuthorized: AuthState()
    data class Error(val msg: String) : AuthState()
}