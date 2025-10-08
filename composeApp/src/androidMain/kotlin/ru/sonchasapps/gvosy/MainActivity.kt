package ru.sonchasapps.gvosy

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import ru.sonchasapps.gvosy.activities.App
import ru.sonchasapps.gvosy.activities.CreateAssistantScreen
import ru.sonchasapps.gvosy.activities.WelcomeScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        setContent {
            CreateAssistantScreen()
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    WelcomeScreen()
}