package ru.sochasapps.gvosynative.presentations.navigation

import android.util.Log
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.koin.compose.viewmodel.koinViewModel
import ru.sochasapps.gvosynative.domain.state.AuthState
import ru.sochasapps.gvosynative.presentations.activities.ApprovedAssistantScreen
import ru.sochasapps.gvosynative.presentations.activities.CreateAssistantScreen
import ru.sochasapps.gvosynative.presentations.activities.HomeScreen
import ru.sochasapps.gvosynative.presentations.activities.LogInScreen
import ru.sochasapps.gvosynative.presentations.activities.SignInScreen
import ru.sochasapps.gvosynative.presentations.activities.SplashScreen
import ru.sochasapps.gvosynative.presentations.activities.WelcomeScreen
import ru.sochasapps.gvosynative.presentations.viewModels.UserViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavigation(userViewModel: UserViewModel) {

    val navController = rememberNavController()
    val state by userViewModel.authState.collectAsState()
    Log.i("RRRR", state.toString())


    LaunchedEffect(state){
        when(state){
            AuthState.Authorized -> {
                navController.navigate("home_screen"){
                    popUpTo("splash_screen") {inclusive = true}
                }
            }
            AuthState.NotAuthorized -> {
                navController.navigate("welcome_screen"){
                    popUpTo("splash_screen") {inclusive = true}
                }
            }

            else -> { TODO("error screen") }
        }
    }

    NavHost(
        navController = navController,
        startDestination = "splash_screen",
    ) {

        composable("splash_screen") {
            SplashScreen(navController)
        }

        composable("welcome_screen") {
            WelcomeScreen(navController)
        }
        composable("sign_in_screen") {
            SignInScreen(navController)
        }
        composable("log_in_screen") {
            LogInScreen(navController)
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