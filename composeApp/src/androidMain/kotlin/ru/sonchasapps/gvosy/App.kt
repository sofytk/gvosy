package ru.sonchasapps.gvosy

import android.app.Application
import android.content.Context
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import ru.sonchasapps.gvosy.database.appModule

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            val context = this@App as Context
            androidContext(context)
            modules(appModule)
        }
    }
}