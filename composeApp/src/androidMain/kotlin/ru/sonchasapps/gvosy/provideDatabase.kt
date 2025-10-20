package ru.sonchasapps.gvosy

import android.content.Context
import androidx.room.Room
import ru.sonchasapps.gvosy.database.UsersDatabase


actual fun provideDatabase(context: Any): UsersDatabase {
    return Room.databaseBuilder(
        context as Context,
        UsersDatabase::class.java,
        "user_database.db"
    ).build()
}