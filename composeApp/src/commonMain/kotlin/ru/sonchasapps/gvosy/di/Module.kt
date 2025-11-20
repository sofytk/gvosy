package ru.sonchasapps.gvosy.di


import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import org.koin.dsl.module
import ru.sonchasapps.gvosy.data.api.AuthApi
import ru.sonchasapps.gvosy.data.repositories.AssistantRepository
import ru.sonchasapps.gvosy.data.repositories.AssistantRepositoryImpl
import ru.sonchasapps.gvosy.data.repositories.UserRepository
import ru.sonchasapps.gvosy.data.repositories.UserRepositoryImpl
import ru.sonchasapps.gvosy.presentations.viewModels.AssistantViewModel
import ru.sonchasapps.gvosy.presentations.viewModels.UserViewModel


val appModule = module {
    single {
        HttpClient() {
            install(ContentNegotiation) {
                json()
            }
        }
    }
    single { AuthApi(get()) }

    single<UserRepository> {
        UserRepositoryImpl(get(), get())
    }
    factory {
        UserViewModel(get())
    }

    single<AssistantRepository> {
        AssistantRepositoryImpl(get())
    }
    factory {
        AssistantViewModel(get())
    }
}