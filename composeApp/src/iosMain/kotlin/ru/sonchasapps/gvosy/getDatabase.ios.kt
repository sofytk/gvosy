package ru.sonchasapps.gvosy

import androidx.room.RoomDatabase
import ru.sonchasapps.gvosy.database.UsersDatabase

//
//fun getDatabaseBuilderIos(): RoomDatabase.Builder<UsersDatabase> {
//    val dbFilePath = NSHomeDirectory() + "/user_database.db"
//    return Room.databaseBuilder<UsersDatabase>(
//        name = dbFilePath,
//        factory =  { AppDatabase::class.instantiateImpl() }  // IDE may show error but there is none.
//    )
//    .setDriver(BundledSQLiteDriver())
//        .build()
//}