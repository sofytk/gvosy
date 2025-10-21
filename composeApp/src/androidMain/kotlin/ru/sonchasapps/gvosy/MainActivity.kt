package ru.sonchasapps.gvosy

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.startKoin

import ru.sonchasapps.gvosy.activities.HomeScreen
import ru.sonchasapps.gvosy.activities.WelcomeScreen
import ru.sonchasapps.gvosy.viewModels.UserViewModel
import ru.sonchasapps.gvosy.database.appModule
import ru.sonchasapps.gvosy.navigation.AppNavigation

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
            AppNavigation(userViewModel)
        }
    }
}
