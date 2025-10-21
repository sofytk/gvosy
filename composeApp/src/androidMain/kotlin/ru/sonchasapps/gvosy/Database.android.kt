package ru.sonchasapps.gvosy

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import ru.sonchasapps.gvosy.database.UsersDatabase
import ru.sonchasapps.gvosy.database.api.UsersApi
import ru.sonchasapps.gvosy.database.dao.UsersDao

val androidDatabaseModule = module {
    single<UsersDatabase> {
        Room.databaseBuilder(
            androidContext(),
            UsersDatabase::class.java,
            UsersDatabase.DATABASE_NAME
        ).build()
    }

    single<UsersDao> { get<UsersDatabase>().getUserDao() }

    // Заглушка для API (реализуйте позже)

    single<UsersApi> {
        object : UsersApi {
            // Пока пустая реализация
        }
    }
}