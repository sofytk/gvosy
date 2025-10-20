package ru.sonchasapps.gvosy.database

import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.room.Room
import org.koin.dsl.module
import ru.sonchasapps.gvosy.database.api.UsersApi
import ru.sonchasapps.gvosy.database.repositories.UserRepository
import ru.sonchasapps.gvosy.database.repositories.UserRepositoryImpl
import ru.sonchasapps.gvosy.provideDatabase


val appModule = module {
    single { provideDatabase(get()) }
    single { get<UsersDatabase>().getUserDao() }
    single {  get<UsersApi>() }
    single<UserRepository> { UserRepositoryImpl(get(), get()) }
    // ViewModel (общая для всех экранов)
    factory { UserViewModel(get()) }

}