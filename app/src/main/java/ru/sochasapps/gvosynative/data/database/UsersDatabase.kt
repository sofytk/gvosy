package ru.sochasapps.gvosynative.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.sochasapps.gvosynative.data.dao.UsersDao
import ru.sochasapps.gvosynative.data.models.UserEntity


@Database(entities = [UserEntity::class], version = 3)
abstract class UsersDatabase : RoomDatabase() {
    abstract fun getUserDao(): UsersDao

    companion object {
        const val DATABASE_NAME = "users_database"
    }
}