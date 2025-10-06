package ru.sonchasapps.gvosy.activities

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ColorScheme

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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import gvosy.composeapp.generated.resources.Res
import gvosy.composeapp.generated.resources.icon_arrow
import gvosy.composeapp.generated.resources.icon_arrow1
import gvosy.composeapp.generated.resources.icon_arrow_drawable
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import ru.sonchasapps.gvosy.theme.ui.theme.AppTheme
import ru.sonchasapps.gvosy.theme.ui.theme.bodyTextSize
import ru.sonchasapps.gvosy.theme.ui.theme.cornerRadius
import ru.sonchasapps.gvosy.theme.ui.theme.titleTextSize


@Composable
@Preview
fun WelcomeScreen() {
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
                Button(
                    modifier = Modifier.height(48.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    ),
                    shape = RoundedCornerShape(cornerRadius),
                    contentPadding = PaddingValues(horizontal = 20.dp, vertical = 10.dp),
                    onClick = {}
                ) {
                    Text(
                        modifier = Modifier.padding(end = 1.dp), text = "Talk to me",
                        color = MaterialTheme.colorScheme.onPrimary,
                        fontSize = bodyTextSize
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Image(
                        painter = painterResource(Res.drawable.icon_arrow1),
                        contentDescription = "Voice icon",
                        modifier = Modifier.size(30.dp)
                    )
                }

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

@Composable
fun Circle(color: Color, x: Float, y: Float) {
    Canvas(modifier = Modifier.fillMaxSize()) {
        drawCircle(
            color = color,
            center = Offset(x = x, y = y),
            radius = 200f,
        )
    }
}

