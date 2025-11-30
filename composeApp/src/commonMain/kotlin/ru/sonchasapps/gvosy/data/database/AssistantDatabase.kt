package ru.sonchasapps.gvosy.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.sonchasapps.gvosy.data.dao.AssistantDao
import ru.sonchasapps.gvosy.data.models.AssistantEntity

@Database(entities = [AssistantEntity::class], version = 1)
abstract class AssistantDatabase : RoomDatabase() {
    abstract fun getAssistantDao() : AssistantDao

    companion object{
        const val DATABASE_NAME = "assistant_database"
    }
}