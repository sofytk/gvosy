package ru.sonchasapps.gvosy.presentations.activities

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import gvosy.composeapp.generated.resources.Res
import gvosy.composeapp.generated.resources.avatar_dove
import gvosy.composeapp.generated.resources.icon_add
import gvosy.composeapp.generated.resources.icon_camera
import gvosy.composeapp.generated.resources.icon_mic
import gvosy.composeapp.generated.resources.icon_notes
import gvosy.composeapp.generated.resources.icon_settings
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import ru.sonchasapps.gvosy.presentations.theme.ui.theme.AppTheme
import ru.sonchasapps.gvosy.presentations.theme.ui.theme.bodyTextSize
import ru.sonchasapps.gvosy.presentations.viewModels.UserViewModel

@Preview
@Composable
fun HomeScreen(navController: NavHostController, userViewModel: UserViewModel) {
    AppTheme() {
        var height by remember { mutableStateOf(0f) }
        var width by remember { mutableStateOf(0f) }
        var text by remember { mutableStateOf("") }
        var assistantName by remember { mutableStateOf("Jane") }

        Column() {
            Spacer(Modifier.height(30.dp))
            Row(
                modifier = Modifier.fillMaxWidth().height(40.dp).padding(top = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Image(
                    painter = painterResource(Res.drawable.icon_notes),
                    contentDescription = "Voice icon",
                    modifier = Modifier.size(30.dp).weight(1f).align(Alignment.CenterVertically)
                )
                Spacer(Modifier.width(50.dp).weight(6f))
                Image(
                    painter = painterResource(Res.drawable.avatar_dove),
                    contentDescription = "Circle Image",
                    contentScale = ContentScale.FillHeight,
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                )
                Text(
                    text = assistantName,
                    modifier = Modifier.weight(2f),
                    color = MaterialTheme.colorScheme.onBackground,
                    fontSize = bodyTextSize
                )
                Spacer(Modifier.width(50.dp).weight(6f))
                Image(
                    painter = painterResource(Res.drawable.icon_settings),
                    contentDescription = "Voice icon",
                    modifier = Modifier.size(30.dp).weight(1f)
                )
            }
        }
        Box(
            modifier = Modifier.fillMaxSize().padding(bottom = 60.dp),
            contentAlignment = Alignment.BottomCenter
        ) {
            Column() {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(IntrinsicSize.Min)
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    IconButton(
                        modifier = Modifier.size(40.dp)
                            .clickable(
                                interactionSource = MutableInteractionSource(),
                                indication = null
                            ) { },
                        colors = IconButtonDefaults.iconButtonColors(
                            contentColor = MaterialTheme.colorScheme.primary,
                            disabledContentColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f)
                        ),
                        onClick = {  }
                    ) {
                        Icon(
                            painter = painterResource(Res.drawable.icon_add),
                            contentDescription = "Add icon",
                            modifier = Modifier.size(24.dp)
                        )
                    }

                    OutlinedTextField(
                        value = text,
                        onValueChange = { text = it },
                        modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = 8.dp),
                        placeholder = {
                            Text(
                                "Text message...",
                                color = MaterialTheme.colorScheme.onBackground
                            )
                        },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                            unfocusedBorderColor = MaterialTheme.colorScheme.primary,
                            focusedTextColor = MaterialTheme.colorScheme.onBackground,
                            unfocusedTextColor = MaterialTheme.colorScheme.onBackground,
                            cursorColor = MaterialTheme.colorScheme.primary,
                        ),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                        shape = RoundedCornerShape(20.dp),
                        singleLine = false,
                        maxLines = 1000
                    )

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(
                            onClick = { /* действие для микрофона */ },
                            modifier = Modifier.size(40.dp)  .clickable(
                                interactionSource = MutableInteractionSource(),
                                indication = null
                            ) { /* действие для микрофона */ },
                            colors = IconButtonDefaults.iconButtonColors(
                                contentColor = MaterialTheme.colorScheme.primary,
                                disabledContentColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f)
                            )
                        ) {
                            Icon(
                                painter = painterResource(Res.drawable.icon_mic),
                                contentDescription = "Voice icon",
                                modifier = Modifier.size(20.dp)
                            )
                        }

                        IconButton(
                            onClick = { /* действие для камеры */ },
                            modifier = Modifier.size(35.dp)  .clickable(
                                interactionSource = MutableInteractionSource(),
                                indication = null
                            ) { /* действие для микрофона */ },
                            colors = IconButtonDefaults.iconButtonColors(
                                contentColor = MaterialTheme.colorScheme.primary,
                                disabledContentColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f)
                            )
                        ) {
                            Icon(
                                painter = painterResource(Res.drawable.icon_camera),
                                contentDescription = "Camera icon",
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }
                }

            }
            Spacer(Modifier.height(30.dp))
        }
    }
}



