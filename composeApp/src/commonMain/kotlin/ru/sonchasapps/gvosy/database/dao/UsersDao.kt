package ru.sonchasapps.gvosy.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow
import ru.sonchasapps.gvosy.database.entities.UserEntity

@Dao
interface UsersDao {

    @Insert()
    suspend fun insert(user: UserEntity)

    @Update
    suspend fun update(user: UserEntity)

    @Upsert
    suspend fun upsert(user: UserEntity)

    @Delete
    suspend fun delete(user: UserEntity)

    @Query("SELECT * from users_database WHERE id = :id")
    fun getUser(id: Long): UserEntity?

}