package ru.sonchasapps.gvosy.presentations.navigation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ru.sonchasapps.gvosy.presentations.activities.ApprovedAssistantScreen
import ru.sonchasapps.gvosy.presentations.activities.CreateAssistantScreen
import ru.sonchasapps.gvosy.presentations.activities.HomeScreen
import ru.sonchasapps.gvosy.presentations.activities.LogUser
import ru.sonchasapps.gvosy.presentations.activities.WelcomeScreen
import ru.sonchasapps.gvosy.presentations.viewModels.UserViewModel
import ru.sonchasapps.gvosy.presentations.viewModels.AssistantViewModel

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
                userViewModel, navController, assistantViewModel = assistantViewModel
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