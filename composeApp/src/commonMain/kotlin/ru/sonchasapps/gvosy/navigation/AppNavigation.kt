package ru.sonchasapps.gvosy.navigation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ru.sonchasapps.gvosy.activities.ApprovedAssistantScreen
import ru.sonchasapps.gvosy.activities.CreateAssistantScreen
import ru.sonchasapps.gvosy.activities.HomeScreen
import ru.sonchasapps.gvosy.activities.LogUser
import ru.sonchasapps.gvosy.activities.WelcomeScreen
import ru.sonchasapps.gvosy.viewModels.UserViewModel
import org.koin.compose.koinInject
import ru.sonchasapps.gvosy.viewModels.AssistantViewModel
import kotlin.getValue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavigation(userViewModel: UserViewModel, assistantViewModel: AssistantViewModel) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "welcome_screen",
    ) {
        composable("welcome_screen") {
            WelcomeScreen(navController)
        }
        composable("log_user_screen") {
            LogUser(navController, viewModel = userViewModel)
        }
        composable("create_assistant_screen") {
            CreateAssistantScreen(
                navController, assistantViewModel = assistantViewModel
            )
        }
        composable("approved_assistant_screen") {
            ApprovedAssistantScreen(navController)
        }
        composable("home_screen") {
            HomeScreen(navController)
        }
    }
}