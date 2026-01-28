package ru.sochasapps.gvosynative.data.repositories


import ru.sochasapps.gvosynative.data.dto.AuthRequest
import ru.sochasapps.gvosynative.data.models.UserEntity
import ru.sochasapps.gvosynative.data.dto.LogInRequest

interface UserRepository {
    suspend fun registerUser(request: AuthRequest): Result<UserEntity>
    suspend fun loginUser(request: LogInRequest): Result<UserEntity>
    suspend fun logout()
    suspend fun getCurrentUser(): UserEntity?
    suspend fun deleteAll()

}