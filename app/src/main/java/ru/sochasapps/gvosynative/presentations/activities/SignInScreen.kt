package ru.sochasapps.gvosynative.presentations.activities

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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

import ru.sochasapps.gvosynative.domain.state.AuthUiState
import ru.sochasapps.gvosynative.presentations.theme.ui.theme.AppTheme
import ru.sochasapps.gvosynative.presentations.theme.ui.theme.btnTextSize
import ru.sochasapps.gvosynative.presentations.theme.ui.theme.cornerRadius
import ru.sochasapps.gvosynative.presentations.theme.ui.theme.cornerRadiusTextField
import ru.sochasapps.gvosynative.presentations.theme.ui.theme.subTitleTextSize
import ru.sochasapps.gvosynative.domain.viewModels.UserViewModel

@Composable
fun SignInScreen(navController: NavHostController, viewModel: UserViewModel) {
    AppTheme {
        var height by remember { mutableStateOf(0f) }
        var width by remember { mutableStateOf(0f) }
        val state by viewModel.state.collectAsState()

        when (state) {
            is AuthUiState.Success -> {
                LaunchedEffect(Unit) {
                    navController.navigate("create_assistant_screen")
                }
            }
            is AuthUiState.Error -> {
                Text(text = "Error: ${(state as AuthUiState.Error).message}")
            }
            is AuthUiState.Loading -> {
                CircularProgressIndicator()
            }
            is AuthUiState.Idle -> Unit
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

            Text(text = "Sign in", fontSize = subTitleTextSize, color = MaterialTheme.colorScheme.onBackground)
            Spacer(Modifier.height((height / 40).dp))

            var userName by rememberSaveable { mutableStateOf("") }
            var userEmail by rememberSaveable { mutableStateOf("") }
            var password by rememberSaveable { mutableStateOf("") }


            OutlinedTextField(
                value = userName,
                onValueChange = { userName = it },
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
                value = userEmail,
                onValueChange = { userEmail = it },
                modifier = Modifier.width((width / 3).dp),
                placeholder = { Text("Email") },
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
                value = password,
                onValueChange = { password = it },
                modifier = Modifier.width((width / 3).dp),
                placeholder = { Text("Password") },
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
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                shape = RoundedCornerShape(cornerRadiusTextField)
            )

            Spacer(Modifier.height((height / 50).dp))

            Button(
                modifier = Modifier.height(54.dp).width((width / 15).dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                ),
                shape = RoundedCornerShape(cornerRadius),
                contentPadding = PaddingValues(horizontal = 20.dp, vertical = 10.dp),
                onClick = {
                    viewModel.registerUser(userName, userEmail, password)
                }
            ) {
                Text(
                    modifier = Modifier.padding(end = 1.dp), text = "Generate",
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontSize = btnTextSize
                )
            }
            Spacer(Modifier.height((height / 60).dp))

            TextButton(
                onClick = { navController.navigate("log_in_screen") }
            ) {
                Text("Already has an account? Log in")
            }
        }
    }
}

