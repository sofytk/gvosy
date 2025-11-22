package ru.sonchasapps.gvosy.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import ru.sonchasapps.gvosy.data.models.UserEntity

@Dao
interface UsersDao {

    @Insert
    suspend fun insert(user: UserEntity)

    @Update
    suspend fun update(user: UserEntity)

    @Upsert
    suspend fun upsert(user: UserEntity)

    @Delete
    suspend fun delete(user: UserEntity)

    @Query("SELECT * from users_database")
    suspend fun getUser(): UserEntity?

    @Query("DELETE FROM users_database")
    suspend fun deleteAll()



}