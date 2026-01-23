package ru.sochasapps.gvosynative.data.repositories


import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.sochasapps.gvosynative.data.api.AuthApi
import ru.sochasapps.gvosynative.data.dao.UsersDao

import ru.sochasapps.gvosynative.data.dto.AuthRequest
import ru.sochasapps.gvosynative.data.models.UserEntity
import ru.sochasapps.gvosynative.data.dto.UserResponse
import ru.sochasapps.gvosynative.data.dto.LogInRequest



class UserRepositoryImpl (private val userDao: UsersDao,
                          private val api: AuthApi, ) : UserRepository {

    override suspend fun registerUser(request: AuthRequest): Result<UserEntity> = withContext(Dispatchers.IO){
        return@withContext try {
            val response = api.register(request)
            println("UserRepositoryImpl: calling registerUser with ${request.userName}")
            val user = UserResponse(
                userName = response.userData.userName,
                userToken = response.accessToken,
                //                usersAssistantId = response.userData.usersAssistantId,
                isPremium = response.userData.isPremium,
            )
            userDao.insert(user.toEntity())
            Result.success(user.toEntity())
        } catch (e: Exception) {
            println("UserRepositoryImpl: error = ${e.message}")
            Result.failure(e)
        }
    }

    override suspend fun loginUser(request: LogInRequest): Result<UserEntity> = withContext(Dispatchers.IO){
        return@withContext try {
            val response = api.login(request)
            val user = UserResponse(
                userName = response.userData.userName,
                userToken = response.accessToken,
    //                usersAssistantId = response.userData.usersAssistantId,
                isPremium = response.userData.isPremium,
            )
            userDao.insert(user.toEntity())
            Result.success(user.toEntity())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun deleteAll() {
        userDao.deleteAll()
    }
    override suspend fun logout() = withContext(Dispatchers.IO){
        userDao.deleteAll()
    }

    override suspend fun getCurrentUser(): UserEntity? = withContext(Dispatchers.IO){
        return@withContext userDao.getUser()
    }


}