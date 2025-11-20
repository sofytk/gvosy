package ru.sonchasapps.gvosy.database.repositories

import ru.sonchasapps.gvosy.database.entities.UserEntity

interface UserRepository {
    suspend fun insertUser(user : UserEntity)
    suspend fun deleteUser(user : UserEntity)
    suspend fun updateUserData(user : UserEntity)
    suspend fun getUserData(id : Long) : UserEntity?
}