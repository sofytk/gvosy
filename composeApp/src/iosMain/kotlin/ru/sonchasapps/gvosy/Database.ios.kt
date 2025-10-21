package ru.sonchasapps.gvosy

import ru.sonchasapps.gvosy.database.UsersDatabase
import ru.sonchasapps.gvosy.database.dao.UsersDao
import org.koin.dsl.module


//val iosDatabaseModule = module {
//    single<UsersDatabase> {
//        Room.databaseBuilder(
//            // iOS-специфичная инициализация
//        ).build()
//    }
//
//    single<UsersDao> { get<UsersDatabase>().getUserDao() }
//
//    single<UsersApi> {
//        object : UsersApi {
//            // iOS реализация API
//        }
//    }
//}