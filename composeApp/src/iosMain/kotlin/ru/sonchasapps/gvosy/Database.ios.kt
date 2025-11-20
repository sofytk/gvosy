package ru.sonchasapps.gvosy


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