package ru.sochasapps.gvosynative

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.ExperimentalMaterial3Api
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.sochasapps.gvosynative.presentations.navigation.AppNavigation
import ru.sochasapps.gvosynative.presentations.viewModels.AssistantViewModel
import ru.sochasapps.gvosynative.presentations.viewModels.MainViewModel
import ru.sochasapps.gvosynative.presentations.viewModels.UserViewModel
import ru.sochasapps.gvosynative.presentations.viewModels.FirstLaunchViewModel

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        val userViewModel: UserViewModel by viewModel()
        userViewModel.checkAuth()
        setContent {
            AppNavigation(userViewModel)
        }
    }

}

