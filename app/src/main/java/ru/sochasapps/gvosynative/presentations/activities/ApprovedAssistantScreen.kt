package ru.sochasapps.gvosynative.presentations.activities

import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import ru.sochasapps.gvosynative.R
import ru.sochasapps.gvosynative.domain.viewModels.FirstLaunchUIState
import ru.sochasapps.gvosynative.domain.viewModels.FirstLaunchViewModel
import ru.sochasapps.gvosynative.presentations.theme.ui.theme.AppTheme
import ru.sochasapps.gvosynative.presentations.theme.ui.theme.bodyTextSize
import ru.sochasapps.gvosynative.presentations.theme.ui.theme.btnTextSize
import ru.sochasapps.gvosynative.presentations.theme.ui.theme.cornerRadius
import ru.sochasapps.gvosynative.presentations.theme.ui.theme.titleTextSize


@Composable
fun ApprovedAssistantScreen(navController: NavHostController, viewModel : FirstLaunchViewModel) {
    AppTheme {
        var height by remember { mutableStateOf(0f) }
        var width by remember { mutableStateOf(0f) }
        val assistantName by remember { mutableStateOf("secret")}
        val uiState by viewModel.uiState.collectAsStateWithLifecycle()

        var showPermissionDialog by remember { mutableStateOf(false) }

        val permissionLauncher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestPermission()
        ) { isGranted ->
            viewModel.onPermissionGranted(isGranted)

            if (isGranted) {
                navController.navigate("home_screen")
            } else {

            }
        }

        when (uiState) {
            is FirstLaunchUIState.Loading -> {

            }

            is FirstLaunchUIState.FirstLaunch -> {
                Unit
            }

            is FirstLaunchUIState.NeedPermission -> {
                PermissionRequestScreen(
                    onRequestPermission = {
                        viewModel.onPermissionAsked()
                        permissionLauncher.launch(Manifest.permission.RECORD_AUDIO)
                    },
                    onSkip = {
                        viewModel.onPermissionGranted(false)
                    }
                )
            }

            is FirstLaunchUIState.PermissionGranted -> {
                navController.navigate("home_screen")
            }

            is FirstLaunchUIState.PermissionPreviouslyDenied -> {
                PermissionPreviouslyDeniedScreen(
                    onRequestAgain = {
                        permissionLauncher.launch(Manifest.permission.RECORD_AUDIO)
                    },
                    onContinue = {
                        navController.navigate("home_screen")
                    }
                )
            }
        }

        if (showPermissionDialog) {
            PermissionDialog(
                onDismiss = { showPermissionDialog = false },
                onConfirm = {
                    showPermissionDialog = false
                    viewModel.onPermissionAsked()
                    permissionLauncher.launch(Manifest.permission.RECORD_AUDIO)
                }
            )
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

            Spacer(Modifier.height((height / 25).dp))

            Text(text = "My name is $assistantName", fontSize = titleTextSize, color = MaterialTheme.colorScheme.onBackground)

            Spacer(Modifier.height((height / 40).dp))

            Image(
                painter = painterResource(R.drawable.avatar_dove),
                contentDescription = "Circle Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(200.dp)
                    .clip(CircleShape)
            )

            Spacer(Modifier.height((height / 50).dp))

            Text(text = "Nice to meet you! Let’s start our chat", fontSize = bodyTextSize, color = MaterialTheme.colorScheme.onBackground)

            Spacer(Modifier.height((height / 37).dp))

            Button(
                modifier = Modifier.height(44.dp).width((width/15).dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                ),
                shape = RoundedCornerShape(cornerRadius),
                contentPadding = PaddingValues(horizontal = 20.dp, vertical = 10.dp),
                onClick = {
                    viewModel.onFirstLaunchCompleted()
                    showPermissionDialog = true
                //    navController.navigate("permission_screen")
                }
            ) {
                Text(
                    modifier = Modifier.padding(end = 1.dp), text = "Start",
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontSize = btnTextSize
                )
            }

        }
    }
}

@Composable
fun PermissionRequestScreen(
    onRequestPermission: () -> Unit,
    onSkip: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Microphone access",
            style = MaterialTheme.typography.headlineMedium,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "The app needs access to a microphone to work with voice messages",
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "You can grant access now or later in the settings",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(48.dp))

        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Button(
                onClick = onRequestPermission,
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.large
            ) {
                Text("Access")
            }

            TextButton(
                onClick = onSkip,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Maybe later")
            }
        }
    }
}

@Composable
fun PermissionPreviouslyDeniedScreen(
    onRequestAgain: () -> Unit,
    onContinue: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = Icons.Default.Warning,
            contentDescription = "Warning",
            modifier = Modifier.size(80.dp),
            tint = MaterialTheme.colorScheme.error
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Access to the microphone is prohibited",
            style = MaterialTheme.typography.headlineMedium,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Earlier, you denied access to the microphone." + "\n" +
                    "Some application functions may not be available.",
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(48.dp))

        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedButton(
                onClick = onRequestAgain,
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.large
            ) {
                Text("Request access again")
            }

            Button(
                onClick = onContinue,
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.large
            ) {
                Text("Continue without access")
            }
        }
    }
}



@Composable
fun PermissionDialog(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(text = "Allow access to the microphone")
        },
        text = {
            Text(
                text = "The app needs access to a microphone to work with audio. " +
                        "You can change this decision in the device settings later."
            )
        },
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text("Access")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Maybe later")
            }
        }
    )
}