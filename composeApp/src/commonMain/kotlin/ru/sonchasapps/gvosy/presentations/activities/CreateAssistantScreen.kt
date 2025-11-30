package ru.sonchasapps.gvosy.presentations.activities

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api

import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import org.jetbrains.compose.ui.tooling.preview.Preview
import ru.sonchasapps.gvosy.data.models.AssistantEntity
import ru.sonchasapps.gvosy.domain.AssistantState
import ru.sonchasapps.gvosy.domain.AuthUiState
import ru.sonchasapps.gvosy.presentations.theme.ui.theme.AppTheme
import ru.sonchasapps.gvosy.presentations.theme.ui.theme.btnTextSize
import ru.sonchasapps.gvosy.presentations.theme.ui.theme.cornerRadius
import ru.sonchasapps.gvosy.presentations.theme.ui.theme.cornerRadiusTextField
import ru.sonchasapps.gvosy.presentations.theme.ui.theme.subTitleTextSize
import ru.sonchasapps.gvosy.presentations.viewModels.AssistantViewModel
import ru.sonchasapps.gvosy.presentations.viewModels.UserViewModel


@ExperimentalMaterial3Api
@Composable
@Preview
fun CreateAssistantScreen(userViewModel: UserViewModel, navController: NavHostController, assistantViewModel: AssistantViewModel) {

    AppTheme {
        var height by remember { mutableStateOf(0f) }
        var width by remember { mutableStateOf(0f) }
        val user by userViewModel.user.collectAsState()
        val state by assistantViewModel.state.collectAsState()

        LaunchedEffect(Unit) {
            userViewModel.getUser()
        }

        when (state) {
            is AssistantState.Success -> {
                LaunchedEffect(Unit) {
                    navController.navigate("approved_assistant_screen")
                }
            }
            is AssistantState.Error -> {
                Text(text = "Error: ${(state as AssistantState.Error).message}")
            }
            is AssistantState.Loading -> {
                CircularProgressIndicator()
            }
            is AssistantState.Idle -> Unit
            else -> {}
        }

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
            Text(text = "Welcome ${user?.userName}!", fontSize = subTitleTextSize, color = MaterialTheme.colorScheme.onBackground)
            Spacer(Modifier.height((height / 40).dp))
            Text(text = "Create your assistant", fontSize = subTitleTextSize, color = MaterialTheme.colorScheme.onBackground)
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
                        start = Offset.Zero,
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
                        start = Offset.Zero,
                        end = Offset.Infinite
                    )
                ),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                shape = RoundedCornerShape(cornerRadiusTextField)
            )

            Spacer(Modifier.height((height / 50).dp))

            val options = listOf("None", "Female", "Male")
            var expanded by remember { mutableStateOf(false) }

            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = {
                    expanded = !expanded
                }
            ) {
                OutlinedTextField(
                    value = assistantSex,
                    readOnly = true,
                    onValueChange = {},
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(
                            expanded = expanded
                        )
                    },
                    modifier = Modifier
                        .width((width / 3).dp)
                        .menuAnchor(MenuAnchorType.PrimaryNotEditable),
                    placeholder = { Text("Gender") },
                    textStyle = TextStyle(
                        brush = Brush.linearGradient(
                            colors = listOf(
                                MaterialTheme.colorScheme.onBackground,
                                MaterialTheme.colorScheme.primary
                            ),
                            start = Offset.Zero,
                            end = Offset.Infinite
                        )
                    ),
                    shape = RoundedCornerShape(cornerRadiusTextField)
                )
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = {
                        expanded = false
                    }
                ) {
                    options.forEach { selectionOption ->
                        DropdownMenuItem(
                            onClick = {
                                assistantSex = selectionOption
                                expanded = false
                            },
                            text = { Text(text = selectionOption) },
                            contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                        )
                    }
                }
            }

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
                        start = Offset.Zero,
                        end = Offset.Infinite
                    )
                ),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                shape = RoundedCornerShape(cornerRadiusTextField)
            )

            Spacer(Modifier.height((height / 30).dp))

            Button(
                modifier = Modifier.height(54.dp).width((width/15).dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                ),
                shape = RoundedCornerShape(cornerRadius),
                contentPadding = PaddingValues(horizontal = 20.dp, vertical = 10.dp),
                onClick = {
                    assistantViewModel.addAssistant(
                        name = assistantName,
                        age = assistantAge.toInt(),
                        desc = assistantDescriptions,
                        sex = assistantSex == "Female"
                    )

                }
            ) {
                Text(
                    modifier = Modifier.padding(end = 1.dp), text = "Generate",
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontSize = btnTextSize
                )
            }
        }
    }
}



