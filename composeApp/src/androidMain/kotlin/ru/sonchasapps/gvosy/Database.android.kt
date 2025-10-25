package ru.sonchasapps.gvosy

import androidx.room.Room
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import ru.sonchasapps.gvosy.database.AssistantDatabase
import ru.sonchasapps.gvosy.database.UsersDatabase
import ru.sonchasapps.gvosy.database.dao.AssistantDao
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

//    single<UsersApi> {
//        object : UsersApi {
//            //TODO()
//        }
//    }

    single<AssistantDatabase> {
        Room.databaseBuilder(
            context = androidContext(),
            klass = AssistantDatabase::class.java,
            name = AssistantDatabase.DATABASE_NAME
        ).build()
    }
    single<AssistantDao> { get<AssistantDatabase>().getAssistantDao()}




}