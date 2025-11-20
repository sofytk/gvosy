package ru.sonchasapps.gvosy

<<<<<<< HEAD
=======
import ru.sonchasapps.gvosy.database.UsersDatabase
import ru.sonchasapps.gvosy.database.dao.UsersDao
import org.koin.dsl.module

>>>>>>> 12631ca0a4545840dae7869c72374147e61760c4

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