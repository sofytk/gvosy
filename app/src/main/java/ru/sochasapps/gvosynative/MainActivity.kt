package ru.sochasapps.gvosynative

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.startKoin
import ru.sochasapps.gvosynative.di.appModule
import ru.sochasapps.gvosynative.presentations.navigation.AppNavigation
import ru.sochasapps.gvosynative.domain.viewModels.AssistantViewModel
import ru.sochasapps.gvosynative.domain.viewModels.MainViewModel
import ru.sochasapps.gvosynative.domain.viewModels.UserViewModel
import androidx.core.content.edit
import ru.sochasapps.gvosynative.domain.viewModels.FirstLaunchViewModel

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        setContent {
            val userViewModel: UserViewModel by viewModel()
            userViewModel.checkAuth()
            val assistantViewModel: AssistantViewModel by viewModel()
            val mainViewModel: MainViewModel by viewModel()
            val firstLaunchViewModel : FirstLaunchViewModel by viewModel()
            AppNavigation(userViewModel, assistantViewModel, mainViewModel, firstLaunchViewModel)
        }
    }

}

