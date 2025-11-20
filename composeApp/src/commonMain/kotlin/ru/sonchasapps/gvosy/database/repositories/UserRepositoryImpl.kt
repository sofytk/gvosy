package ru.sonchasapps.gvosy.database.repositories


import ru.sonchasapps.gvosy.database.dao.UsersDao
import ru.sonchasapps.gvosy.database.entities.UserEntity

class UserRepositoryImpl (private val userDao: UsersDao,
                          /*private val apiService: UsersApi,*/
                          /*private val syncManager: DataSyncManager*/) : UserRepository{


        override suspend fun insertUser(user: UserEntity) {
            userDao.insert(user)
        }

        override suspend fun deleteUser(user: UserEntity) {
            userDao.delete(user)
        }

        override suspend fun updateUserData(user: UserEntity) {
            userDao.update(user)
        }

        override suspend fun getUserData(id: Long): UserEntity? {
            val user : UserEntity? = userDao.getUser(id)
            return user
        }

    }