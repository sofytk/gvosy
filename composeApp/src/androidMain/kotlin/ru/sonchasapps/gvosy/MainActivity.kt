package ru.sonchasapps.gvosy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import ru.sonchasapps.gvosy.activities.HomeScreen
import ru.sonchasapps.gvosy.activities.WelcomeScreen
import ru.sonchasapps.gvosy.navigation.AppNavigation

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        setContent {
            AppNavigation()
        }
    }
}
