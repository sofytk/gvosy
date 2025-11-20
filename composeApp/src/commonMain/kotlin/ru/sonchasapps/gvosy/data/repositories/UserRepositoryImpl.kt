package ru.sonchasapps.gvosy.data.repositories


import androidx.collection.IntSet
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import ru.sonchasapps.gvosy.data.api.AuthApi
import ru.sonchasapps.gvosy.data.dao.UsersDao
import ru.sonchasapps.gvosy.data.models.UserEntity
import ru.sonchasapps.gvosy.data.models.AuthRequest
import ru.sonchasapps.gvosy.data.models.LogInRequest
import ru.sonchasapps.gvosy.data.models.UserResponse
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

class UserRepositoryImpl (private val userDao: UsersDao,
                          private val api: AuthApi,
                          /*private val syncManager: DataSyncManager*/) : UserRepository{

    override suspend fun registerUser(request: AuthRequest): Result<UserEntity> = withContext(Dispatchers.IO){
        return@withContext try {
            val response = api.register(request)
            println("UserRepositoryImpl: calling registerUser with ${request.userName}")
            val user = UserResponse(
                userName = response.userData.userName,
                userEmail = response.userData.userEmail,
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
                userEmail =  response.userData.userEmail,
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

    override suspend fun logout(user : UserEntity) = withContext(Dispatchers.IO){
        userDao.delete(user)
    }

    override suspend fun getCurrentUser(): UserEntity? = withContext(Dispatchers.IO){
        return@withContext userDao.getUser()
    }


}