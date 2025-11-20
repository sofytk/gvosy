package ru.sonchasapps.gvosy.database


import org.koin.dsl.module
import ru.sonchasapps.gvosy.database.repositories.AssistantRepository
import ru.sonchasapps.gvosy.database.repositories.AssistantRepositoryImpl
import ru.sonchasapps.gvosy.database.repositories.UserRepository
import ru.sonchasapps.gvosy.database.repositories.UserRepositoryImpl
import ru.sonchasapps.gvosy.viewModels.UserViewModel


val appModule = module {
    single<UserRepository> {
        UserRepositoryImpl(get())
    }
    factory {
        UserViewModel(get())
    }

    single<AssistantRepository> {
        AssistantRepositoryImpl(get())
    }
}