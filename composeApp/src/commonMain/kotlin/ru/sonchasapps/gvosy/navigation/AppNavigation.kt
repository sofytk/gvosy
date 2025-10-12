package ru.sonchasapps.gvosy.navigation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ru.sonchasapps.gvosy.activities.ApprovedAssistantScreen
import ru.sonchasapps.gvosy.activities.CreateAssistantScreen
import ru.sonchasapps.gvosy.activities.HomeScreen
import ru.sonchasapps.gvosy.activities.WelcomeScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "welcome_screen",
    ) {
        composable("welcome_screen") {
            WelcomeScreen(navController)
        }
        composable("create_assistant_screen") {
            CreateAssistantScreen(navController)
        }
        composable("approved_assistant_screen") {
            ApprovedAssistantScreen(navController)
        }
        composable("home_screen") {
            HomeScreen(navController)
        }
    }
}