package ru.sonchasapps.gvosy.presentations.activities

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import gvosy.composeapp.generated.resources.Res
import gvosy.composeapp.generated.resources.avatar_dove
import org.jetbrains.compose.resources.painterResource
import ru.sonchasapps.gvosy.presentations.theme.ui.theme.AppTheme
import ru.sonchasapps.gvosy.presentations.theme.ui.theme.bodyTextSize
import ru.sonchasapps.gvosy.presentations.theme.ui.theme.btnTextSize
import ru.sonchasapps.gvosy.presentations.theme.ui.theme.cornerRadius
import ru.sonchasapps.gvosy.presentations.theme.ui.theme.titleTextSize


@Composable
fun ApprovedAssistantScreen(navController: NavHostController) {
    AppTheme {
        var height by remember { mutableStateOf(0f) }
        var width by remember { mutableStateOf(0f) }
        val assistantName by remember { mutableStateOf("Lame")}
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .fillMaxSize()
                .onGloballyPositioned { coors ->
                    height = coors.size.height.toFloat()
                    width = coors.size.width.toFloat()
                },
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(Modifier.height((height / 25).dp))

            Text(text = "My name is $assistantName", fontSize = titleTextSize)

            Spacer(Modifier.height((height / 40).dp))

            Image(
                painter = painterResource(Res.drawable.avatar_dove),
                contentDescription = "Circle Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(200.dp)
                    .clip(CircleShape)
            )

            Spacer(Modifier.height((height / 50).dp))

            Text(text = "Nice to meet you! Let’s start our chat", fontSize = bodyTextSize)

            Spacer(Modifier.height((height / 37).dp))

            Button(
                modifier = Modifier.height(44.dp).width((width/15).dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                ),
                shape = RoundedCornerShape(cornerRadius),
                contentPadding = PaddingValues(horizontal = 20.dp, vertical = 10.dp),
                onClick = { navController.navigate("home_screen") }
            ) {
                Text(
                    modifier = Modifier.padding(end = 1.dp), text = "Start",
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontSize = btnTextSize
                )
            }
            Spacer(Modifier.height((height/90).dp))
            Button(
                modifier = Modifier.height(44.dp).width((width/15).dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    contentColor = MaterialTheme.colorScheme.onBackground
                ),
                border = BorderStroke(2.dp, MaterialTheme.colorScheme.primary),
                shape = RoundedCornerShape(cornerRadius),
                contentPadding = PaddingValues(horizontal = 20.dp, vertical = 10.dp),
                onClick = { navController.popBackStack() }
            ) {
                Text(
                    modifier = Modifier.padding(end = 1.dp), text = "Reply",
                    color = MaterialTheme.colorScheme.onBackground,
                    fontSize = btnTextSize
                )
            }
        }
    }
}