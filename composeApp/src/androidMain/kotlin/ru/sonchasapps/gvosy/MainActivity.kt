package ru.sonchasapps.gvosy

import android.os.Bundle
<<<<<<< HEAD
=======
import android.util.Log
>>>>>>> 12631ca0a4545840dae7869c72374147e61760c4
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.ExperimentalMaterial3Api
<<<<<<< HEAD
=======
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
>>>>>>> 12631ca0a4545840dae7869c72374147e61760c4
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.startKoin

<<<<<<< HEAD
import ru.sonchasapps.gvosy.presentations.viewModels.UserViewModel
import ru.sonchasapps.gvosy.di.appModule
import ru.sonchasapps.gvosy.presentations.navigation.AppNavigation
import ru.sonchasapps.gvosy.presentations.viewModels.AssistantViewModel
=======
import ru.sonchasapps.gvosy.activities.HomeScreen
import ru.sonchasapps.gvosy.activities.WelcomeScreen
import ru.sonchasapps.gvosy.viewModels.UserViewModel
import ru.sonchasapps.gvosy.database.appModule
import ru.sonchasapps.gvosy.navigation.AppNavigation
import ru.sonchasapps.gvosy.viewModels.AssistantViewModel
>>>>>>> 12631ca0a4545840dae7869c72374147e61760c4

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
