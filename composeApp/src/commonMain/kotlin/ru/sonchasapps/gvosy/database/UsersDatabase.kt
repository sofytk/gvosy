package ru.sonchasapps.gvosy.database

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.internal.synchronized
import ru.sonchasapps.gvosy.database.dao.UsersDao
import ru.sonchasapps.gvosy.database.entities.User
import kotlin.concurrent.Volatile

@Database(entities = [User::class], version = 1, exportSchema = false)
abstract class UsersDatabase : RoomDatabase() {
    abstract fun getUserDao(): UsersDao
}