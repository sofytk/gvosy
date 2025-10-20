package ru.sonchasapps.gvosy.database.repositories

import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import ru.sonchasapps.gvosy.database.api.UsersApi
import ru.sonchasapps.gvosy.database.dao.UsersDao
import ru.sonchasapps.gvosy.database.entities.User

class UserRepositoryImpl (private val userDao: UsersDao,
                      private val apiService: UsersApi,
                      /*private val syncManager: DataSyncManager*/) : UserRepository{


    override suspend fun insertUser(user: User) {
        userDao.insert(user)
    }

    override suspend fun deleteUser(user: User) {

            userDao.delete(user)
    }

    override suspend fun updateUserData(user: User) {
        TODO("Not yet implemented")
    }

    override suspend fun getUserData(id: Long): User? {
        TODO("Not yet implemented")
    }

}