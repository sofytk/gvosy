package ru.sonchasapps.gvosy

import androidx.room.Room
<<<<<<< HEAD
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import ru.sonchasapps.gvosy.data.database.AssistantDatabase
import ru.sonchasapps.gvosy.data.database.UsersDatabase
import ru.sonchasapps.gvosy.data.dao.AssistantDao
import ru.sonchasapps.gvosy.data.dao.UsersDao

val androidDatabaseModule = module {
    single<UsersDatabase> {
        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("""
            CREATE TABLE IF NOT EXISTS users_database (
                id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                userName TEXT NOT NULL,
                userEmail TEXT,
                userToken TEXT,
                isPremium INTEGER NOT NULL DEFAULT 0
            )
        """.trimIndent())
                database.execSQL("DROP TABLE users")
            }
        }
=======
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import ru.sonchasapps.gvosy.database.AssistantDatabase
import ru.sonchasapps.gvosy.database.UsersDatabase
import ru.sonchasapps.gvosy.database.dao.AssistantDao
import ru.sonchasapps.gvosy.database.dao.UsersDao

val androidDatabaseModule = module {


    single<UsersDatabase> {
>>>>>>> 12631ca0a4545840dae7869c72374147e61760c4
        Room.databaseBuilder(
            androidContext(),
            UsersDatabase::class.java,
            UsersDatabase.DATABASE_NAME
<<<<<<< HEAD
        ).addMigrations(MIGRATION_1_2).build()
    }
    single<UsersDao> { get<UsersDatabase>().getUserDao() }

=======
        ).build()
    }
    single<UsersDao> { get<UsersDatabase>().getUserDao() }

//    single<UsersApi> {
//        object : UsersApi {
//            //TODO()
//        }
//    }

>>>>>>> 12631ca0a4545840dae7869c72374147e61760c4
    single<AssistantDatabase> {
        Room.databaseBuilder(
            context = androidContext(),
            klass = AssistantDatabase::class.java,
            name = AssistantDatabase.DATABASE_NAME
        ).build()
    }
<<<<<<< HEAD
    single<AssistantDao> { get<AssistantDatabase>().getAssistantDao() }
=======
    single<AssistantDao> { get<AssistantDatabase>().getAssistantDao()}



>>>>>>> 12631ca0a4545840dae7869c72374147e61760c4

}