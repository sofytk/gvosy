package ru.sochasapps.gvosynative.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.sochasapps.gvosynative.data.dao.AssistantDao
import ru.sochasapps.gvosynative.data.models.AssistantEntity

@Database(entities = [AssistantEntity::class], version = 1)
abstract class AssistantDatabase : RoomDatabase() {
    abstract fun getAssistantDao() : AssistantDao

    companion object{
        const val DATABASE_NAME = "assistant_database"
    }
}