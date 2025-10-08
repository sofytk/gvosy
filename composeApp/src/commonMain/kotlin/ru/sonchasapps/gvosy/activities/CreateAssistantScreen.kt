package ru.sonchasapps.gvosy.activities

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.ui.tooling.preview.Preview
import ru.sonchasapps.gvosy.theme.ui.theme.AppTheme
import ru.sonchasapps.gvosy.theme.ui.theme.bodyTextSize
import ru.sonchasapps.gvosy.theme.ui.theme.cornerRadius
import ru.sonchasapps.gvosy.theme.ui.theme.cornerRadiusTextField
import ru.sonchasapps.gvosy.ui_components.MyTextField

@Composable
@Preview

fun CreateAssistantScreen() {

    AppTheme {
        var height by remember { mutableStateOf(0f) }
        var width by remember { mutableStateOf(0f) }
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

            Spacer(Modifier.height((height / 10).dp))

            Text(text = "Create your assistant", fontSize = bodyTextSize)
            Spacer(Modifier.height((height / 40).dp))

            var assistantName by rememberSaveable { mutableStateOf("") }
            var assistantAge by rememberSaveable { mutableStateOf("") }
            var assistantSex by rememberSaveable { mutableStateOf("") }
            var assistantDescriptions by rememberSaveable { mutableStateOf("") }

            OutlinedTextField(
                value = assistantName,
                onValueChange = { assistantName = it },
                modifier = Modifier.width((width / 3).dp),
                placeholder = { Text("Name") },
                textStyle = TextStyle(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.onBackground,
                            MaterialTheme.colorScheme.primary
                        ),
                        start = Offset(0f, 5f),
                        end = Offset.Infinite
                    )
                ),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                shape = RoundedCornerShape(cornerRadiusTextField)
            )

            Spacer(Modifier.height((height / 50).dp))

            OutlinedTextField(
                value = assistantAge,
            onValueChange = { assistantAge = it },
            modifier = Modifier.width((width / 3).dp),
            placeholder = { Text("Age") },
            textStyle = TextStyle(
                brush = Brush.linearGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.onBackground,
                        MaterialTheme.colorScheme.primary
                    ),
                    start = Offset(0f, 5f),
                    end = Offset.Infinite
                )
            ),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            shape = RoundedCornerShape(cornerRadiusTextField)
            )

            Spacer(Modifier.height((height / 50).dp))
            OutlinedTextField(
                value = assistantSex,
                onValueChange = { assistantSex = it },
                modifier = Modifier.width((width / 3).dp),
                placeholder = { Text("Mela/Female") },
                textStyle = TextStyle(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.onBackground,
                            MaterialTheme.colorScheme.primary
                        ),
                        start = Offset(0f, 5f),
                        end = Offset.Infinite
                    )
                ),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                shape = RoundedCornerShape(cornerRadiusTextField)
            )
            Spacer(Modifier.height((height / 50).dp))

            OutlinedTextField(
                value = assistantDescriptions,
                onValueChange = { assistantDescriptions = it },
                modifier = Modifier.width((width / 3).dp),
                placeholder = { Text("Habits, behavior, and personality traits") },
                textStyle = TextStyle(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.onBackground,
                            MaterialTheme.colorScheme.primary
                        ),
                        start = Offset(0f, 1f),
                        end = Offset.Infinite
                    )
                ),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                shape = RoundedCornerShape(cornerRadiusTextField)
            )
        }
    }
}

