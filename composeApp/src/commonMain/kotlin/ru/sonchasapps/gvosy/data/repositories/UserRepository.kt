package ru.sonchasapps.gvosy.data.repositories

import ru.sonchasapps.gvosy.data.models.UserEntity
import ru.sonchasapps.gvosy.data.models.AuthRequest
import ru.sonchasapps.gvosy.data.models.LogInRequest

interface UserRepository {
    suspend fun registerUser(request: AuthRequest): Result<UserEntity>
    suspend fun loginUser(request: LogInRequest): Result<UserEntity>
    suspend fun logout(user : UserEntity)
    suspend fun getCurrentUser(): UserEntity?
}