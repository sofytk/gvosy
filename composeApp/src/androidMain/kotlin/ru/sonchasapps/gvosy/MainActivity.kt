package ru.sonchasapps.gvosy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.ExperimentalMaterial3Api
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.startKoin

import ru.sonchasapps.gvosy.presentations.viewModels.UserViewModel
import ru.sonchasapps.gvosy.di.appModule
import ru.sonchasapps.gvosy.presentations.navigation.AppNavigation
import ru.sonchasapps.gvosy.presentations.viewModels.AssistantViewModel

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        startKoin {
            androidContext(this@MainActivity)
            modules(appModule + androidDatabaseModule)
        }

        setContent {
            val userViewModel: UserViewModel by viewModel()
            val assistantViewModel: AssistantViewModel by viewModel()
            AppNavigation(userViewModel, assistantViewModel)
        }
    }
}
