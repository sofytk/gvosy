package ru.sochasapps.gvosynative.presentations.activities

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import ru.sochasapps.gvosynative.presentations.theme.ui.theme.AppTheme
import ru.sochasapps.gvosynative.presentations.theme.ui.theme.bodyTextSize
import ru.sochasapps.gvosynative.presentations.theme.ui.theme.titleTextSize


@Composable
@Preview
fun SplashScreen(navController: NavHostController) {
    AppTheme {
        var showContent by remember { mutableStateOf(false) }
        var height by remember { mutableStateOf(0f) }
        var width by remember { mutableStateOf(0f) }
        Column(
            modifier = Modifier.background(color = MaterialTheme.colorScheme.background)
                .fillMaxSize().onGloballyPositioned { coordinates ->
                height = coordinates.size.height.toFloat()
                width = coordinates.size.width.toFloat()
            },
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(Modifier.height((height/6).dp))
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val arrayOfColors = ArrayList<Color>()
                arrayOfColors.add(MaterialTheme.colorScheme.primary)
                arrayOfColors.add(MaterialTheme.colorScheme.tertiary)
                arrayOfColors.add(MaterialTheme.colorScheme.primary)
                Text(
                    text = "Gvosy",
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = titleTextSize,
                    fontWeight = FontWeight.Bold,
                    style = TextStyle(brush = Brush.linearGradient(colors = arrayOfColors))
                )
                Spacer(Modifier.height(70.dp))
                Text(
                    text = "Loading...",
                    color = MaterialTheme.colorScheme.onBackground,
                    fontSize = bodyTextSize,
                    fontWeight = FontWeight.Bold,
                    style = TextStyle(brush = Brush.linearGradient(colors = arrayOfColors))
                )
            }
        }

        Box(Modifier.blur(200.dp)) {
            Box(Modifier.fillMaxSize()) {
                Circle(MaterialTheme.colorScheme.primary, x = width / 8, height / 8 * 0.2f)
                Circle(MaterialTheme.colorScheme.secondary, x = width / 5 * 0.5f, height / 4 * 3)
                Circle(MaterialTheme.colorScheme.primary, x = width / 8 * 8.2f, height / 2)
                Circle(MaterialTheme.colorScheme.tertiary, x = width / 8 * 7.6f, height / 4 * 3.8f)
            }
        }

    }
}


