package ru.sonchasapps.gvosy.database


import androidx.room.Database
import androidx.room.RoomDatabase
import ru.sonchasapps.gvosy.database.dao.UsersDao
import ru.sonchasapps.gvosy.database.entities.UserEntity

@Database(entities = [UserEntity::class], version = 1, exportSchema = false)
abstract class UsersDatabase : RoomDatabase() {
    abstract fun getUserDao(): UsersDao

    companion object {
        const val DATABASE_NAME = "users_database"
    }
}
