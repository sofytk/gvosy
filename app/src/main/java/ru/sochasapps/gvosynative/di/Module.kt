package ru.sochasapps.gvosynative.di

import androidx.datastore.core.DataStore
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.room.Room
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.websocket.WebSockets
import io.ktor.client.plugins.websocket.pingInterval
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import ru.sochasapps.gvosynative.AppPreferences
import ru.sochasapps.gvosynative.data.api.AssistantApi
import ru.sochasapps.gvosynative.data.api.AuthApi
import ru.sochasapps.gvosynative.data.api.MessageApiService
import ru.sochasapps.gvosynative.data.dao.AssistantDao
import ru.sochasapps.gvosynative.data.dao.UsersDao
import ru.sochasapps.gvosynative.data.database.AssistantDatabase
import ru.sochasapps.gvosynative.data.database.UsersDatabase
import ru.sochasapps.gvosynative.data.repositories.AssistantRepository
import ru.sochasapps.gvosynative.data.repositories.AssistantRepositoryImpl
import ru.sochasapps.gvosynative.data.repositories.S3Repository
import ru.sochasapps.gvosynative.data.repositories.UserRepository
import ru.sochasapps.gvosynative.data.repositories.UserRepositoryImpl
import ru.sochasapps.gvosynative.data.repositories.VoiceMessageRepository
import ru.sochasapps.gvosynative.data.repositories.VoiceMessageRepositoryImpl
import ru.sochasapps.gvosynative.data.websocket.WebSocketService
import ru.sochasapps.gvosynative.data.websocket.WebSocketServiceImpl
import ru.sochasapps.gvosynative.domain.service.VoiceRecorder
import ru.sochasapps.gvosynative.presentations.viewModels.AssistantViewModel
import ru.sochasapps.gvosynative.presentations.viewModels.ChatViewModel
import ru.sochasapps.gvosynative.presentations.viewModels.FirstLaunchViewModel
import ru.sochasapps.gvosynative.presentations.viewModels.MainViewModel
import ru.sochasapps.gvosynative.presentations.viewModels.UserViewModel
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.toDuration


val appModule = module {
    single {
        HttpClient() {
            install(ContentNegotiation) {
                json()
            }
            install(WebSockets) {
                maxFrameSize = Long.MAX_VALUE
                pingInterval = 2000.milliseconds
            }
        }
    }
    single { AuthApi(get()) }

    single<DataStore<Preferences>> {
        PreferenceDataStoreFactory.create(
            corruptionHandler = ReplaceFileCorruptionHandler(
                produceNewData = { emptyPreferences() }
            ),
            migrations = listOf(),
            scope = CoroutineScope(Dispatchers.IO + SupervisorJob()),
            produceFile = { androidContext().preferencesDataStoreFile("app_preferences") }
        )
    }

    single<MessageApiService> { get() }

    single<WebSocketService> { WebSocketServiceImpl(get()) }

    single<AppPreferences> { AppPreferences(get()) }

    single<UserRepository> {
        UserRepositoryImpl(get(), get())
    }

    single{ AssistantApi(get()) }

    single<AssistantRepository> {
        AssistantRepositoryImpl(get(), get())
    }

    single<UsersDatabase> {
        Room.databaseBuilder(
            androidContext(),
            UsersDatabase::class.java,
            UsersDatabase.DATABASE_NAME
        ).build()
    }
    single<UsersDao> { get<UsersDatabase>().getUserDao() }

    single<AssistantDatabase> {
        Room.databaseBuilder(
            context = androidContext(),
            klass = AssistantDatabase::class.java,
            name = AssistantDatabase.DATABASE_NAME
        ).build()
    }
    single<AssistantDao> { get<AssistantDatabase>().getAssistantDao() }

    single<VoiceRecorder> { VoiceRecorder(androidContext()) }
    factory { MainViewModel(get(), get()) }

    single<MessageApiService> { MessageApiService(get()) }

    single<VoiceMessageRepository> { VoiceMessageRepositoryImpl(get(), get(), get(), get()) }

    single<S3Repository> { S3Repository(androidContext()) }

    viewModel { ChatViewModel(get(), get(), get(), get(), get(), get()) }
    viewModel { AssistantViewModel(get(), get()) }
    viewModel { UserViewModel(get()) }
    viewModel { FirstLaunchViewModel(get()) }

}