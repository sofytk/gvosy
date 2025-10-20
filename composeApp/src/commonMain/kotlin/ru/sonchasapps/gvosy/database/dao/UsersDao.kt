package ru.sonchasapps.gvosy.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow
import ru.sonchasapps.gvosy.database.entities.User

@Dao
interface UsersDao {

    @Insert()
    suspend fun insert(user: User)

    @Update
    suspend fun update(user: User)

    @Upsert
    suspend fun upsert(user: User)

    @Delete
    suspend fun delete(user: User)

    @Query("SELECT * from users WHERE id = :id")
    fun getUser(id: Long): Flow<User>

}