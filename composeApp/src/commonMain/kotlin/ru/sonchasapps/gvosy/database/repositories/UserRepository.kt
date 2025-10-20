package ru.sonchasapps.gvosy.database.repositories

import ru.sonchasapps.gvosy.database.entities.User

interface UserRepository {
    suspend fun insertUser(user : User)
    suspend fun deleteUser(user : User)
    suspend fun updateUserData(user : User)
    suspend fun getUserData(id : Long) : User?
}