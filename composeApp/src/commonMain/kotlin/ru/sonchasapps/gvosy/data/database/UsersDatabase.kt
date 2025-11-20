package ru.sonchasapps.gvosy.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.sonchasapps.gvosy.data.dao.UsersDao
import ru.sonchasapps.gvosy.data.models.UserEntity

@Database(entities = [UserEntity::class], version = 2, exportSchema = false)
abstract class UsersDatabase : RoomDatabase() {
    abstract fun getUserDao(): UsersDao

    companion object {
        const val DATABASE_NAME = "users_database"
    }
}