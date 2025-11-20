package ru.sonchasapps.gvosy.database

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.sonchasapps.gvosy.database.dao.AssistantDao
import ru.sonchasapps.gvosy.database.entities.AssistantEntity


@Database(entities = [AssistantEntity::class], version = 1, exportSchema = false)
abstract class AssistantDatabase : RoomDatabase() {
    abstract fun getAssistantDao() : AssistantDao

    companion object{
        const val DATABASE_NAME = "assistant_database"
    }
}