package ru.sochasapps.gvosynative.presentations.activities

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.compose.viewmodel.koinViewModel
import ru.sochasapps.gvosynative.R
import ru.sochasapps.gvosynative.data.models.ActionType
import ru.sochasapps.gvosynative.data.models.MessageEntity
import ru.sochasapps.gvosynative.data.models.RecordedAudio
import ru.sochasapps.gvosynative.presentations.component.MessageItem
import ru.sochasapps.gvosynative.presentations.theme.ui.theme.AppTheme
import ru.sochasapps.gvosynative.presentations.theme.ui.theme.bodyTextSize
import ru.sochasapps.gvosynative.presentations.viewModels.ChatViewModel
import ru.sochasapps.gvosynative.presentations.viewModels.MainViewModel
import ru.sochasapps.gvosynative.presentations.viewModels.UserViewModel

@SuppressLint("RememberInComposition")
@Composable
fun HomeScreen(
    navController: NavHostController,
    userViewModel: UserViewModel = koinViewModel(),
    mainViewModel: MainViewModel = koinViewModel(),
    chatViewModel: ChatViewModel = koinViewModel()
) {
    val uploadState by mainViewModel.uploadState.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    val chatMessages by chatViewModel.chatMessages.collectAsState()
    val isLoading by chatViewModel.isLoading.collectAsState()

    AppTheme() {
        var text by remember { mutableStateOf("") }
        var assistantName by remember { mutableStateOf("Jane") }

        val listState = rememberLazyListState()
        LaunchedEffect(chatMessages.size) {
            if (chatMessages.isNotEmpty()) {
                listState.animateScrollToItem(0)
            }
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                Spacer(Modifier.height(30.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp)
                        .padding(top = 10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    Image(
                        painter = painterResource(R.drawable.icon_notes),
                        contentDescription = "Notes icon",
                        modifier = Modifier
                            .size(30.dp)
                            .weight(1f)
                            .align(Alignment.CenterVertically)
                    )
                    Spacer(Modifier
                        .width(50.dp)
                        .weight(6f))
                    Image(
                        painter = painterResource(R.drawable.avatar_dove),
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
                    Spacer(Modifier
                        .width(50.dp)
                        .weight(6f))
                    Image(
                        painter = painterResource(R.drawable.icon_settings),
                        contentDescription = "Settings icon",
                        modifier = Modifier
                            .size(30.dp)
                            .weight(1f)
                    )
                }

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(bottom = 150.dp)
                        .weight(1f)
                        .background(MaterialTheme.colorScheme.background)
                ) {
                    LazyColumn(
                        state = listState,
                        modifier = Modifier.fillMaxSize(),
                        reverseLayout = true
                    ) {
                        items(chatMessages.size) { index ->
                            val message = chatMessages[index]
                            MessageItem(
                                message = message,
                                onActionClick = { action ->
                                    chatViewModel.onActionClick(action)
                                    when (action.type) {
                                        ActionType.NOTE -> {}
                                        ActionType.TODO_LIST -> {}
                                        else -> {}
                                    }
                                }
                            )
                        }
                    }
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                when (uploadState) {
                    is MainViewModel.UploadState.Uploading -> {
                        Box(
                            modifier = Modifier
                                .size(80.dp)
                                .background(
                                    MaterialTheme.colorScheme.surfaceContainer.copy(alpha = 0.9f),
                                    RoundedCornerShape(16.dp)
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                CircularProgressIndicator(
                                    color = MaterialTheme.colorScheme.primary
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = "Загрузка...",
                                    color = MaterialTheme.colorScheme.onSurface,
                                    fontSize = 12.sp
                                )
                            }
                        }
                    }

                    is MainViewModel.UploadState.Success -> {
                        val audioUrl = (uploadState as MainViewModel.UploadState.Success).audioUrl

                        LaunchedEffect(audioUrl) {
                            chatViewModel.sendVoiceMessage(audioUrl)
                            delay(2000)
                            mainViewModel.resetUploadState()
                        }

                        AnimatedVisibility(
                            visible = true,
                            enter = fadeIn() + slideInVertically(),
                            exit = fadeOut() + slideOutVertically()
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(80.dp)
                                    .background(
                                        MaterialTheme.colorScheme.surfaceContainer.copy(alpha = 0.9f),
                                        RoundedCornerShape(16.dp)
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {

                                    Icon(
                                        painter = painterResource(R.drawable.icon_mic),
                                        contentDescription = "Success",
                                        tint = MaterialTheme.colorScheme.primary,
                                        modifier = Modifier.size(24.dp)
                                    )
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Text(
                                        text = "Готово!",
                                        color = MaterialTheme.colorScheme.onSurface,
                                        fontSize = 12.sp
                                    )
                                }
                            }
                        }
                    }

                    is MainViewModel.UploadState.Error -> {
                        val errorMessage = (uploadState as MainViewModel.UploadState.Error).message

                        LaunchedEffect(errorMessage) {
                            snackbarHostState.showSnackbar(
                                message = errorMessage,
                                duration = androidx.compose.material3.SnackbarDuration.Short
                            )
                            mainViewModel.resetUploadState()
                        }
                    }

                    MainViewModel.UploadState.Idle -> {
                    }
                }
            }

            SnackbarHost(
                hostState = snackbarHostState,
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(bottom = 500.dp)
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 60.dp),
                contentAlignment = Alignment.BottomCenter
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.background)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(IntrinsicSize.Min)
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        IconButton(
                            modifier = Modifier
                                .size(40.dp)
                                .clickable(
                                    interactionSource = MutableInteractionSource(),
                                    indication = null
                                ) { },
                            colors = IconButtonDefaults.iconButtonColors(
                                contentColor = MaterialTheme.colorScheme.primary,
                                disabledContentColor = MaterialTheme.colorScheme.onSurface.copy(
                                    alpha = 0.12f
                                )
                            ),
                            onClick = { userViewModel.logout() }
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.icon_add),
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
                            VoiceRecordButton(
                                mainViewModel = mainViewModel,
                                onRecorded = { audio ->
                                    coroutineScope.launch {
                                        mainViewModel.stopRecordingAndUpload()
                                    }
                                },
                                isUploading = uploadState is MainViewModel.UploadState.Uploading
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun VoiceRecordButton(
    mainViewModel: MainViewModel,
    onRecorded: (RecordedAudio) -> Unit,
    isUploading: Boolean = false
) {
    val isRecording by mainViewModel.isRecording.collectAsState()
    val haptic = LocalHapticFeedback.current
    var isLocked by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    var recordingTime by remember { mutableStateOf(0) }

    LaunchedEffect(isRecording) {
        if (isRecording) {
            recordingTime = 0
            while (isRecording) {
                delay(1000)
                recordingTime++
            }
        } else {
            recordingTime = 0
        }
    }

    val scale by animateFloatAsState(
        targetValue = if (isRecording) 1.15f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        )
    )

    val infiniteTransition = rememberInfiniteTransition()
    val pulseScale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.08f,
        animationSpec = infiniteRepeatable(
            animation = tween(1500, easing = CubicBezierEasing(0.4f, 0f, 0.2f, 1f)),
            repeatMode = RepeatMode.Reverse
        )
    )

    val waveAlpha by infiniteTransition.animateFloat(
        initialValue = 0.5f,
        targetValue = 0f,
        animationSpec = infiniteRepeatable(
            animation = tween(1500),
            repeatMode = RepeatMode.Restart
        )
    )

    val waveScale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 2.5f,
        animationSpec = infiniteRepeatable(
            animation = tween(1500),
            repeatMode = RepeatMode.Restart
        )
    )

    Box(
        contentAlignment = Alignment.Center
    ) {
        AnimatedVisibility(
            visible = isRecording && recordingTime >= 0,
            enter = fadeIn() + slideInVertically(
                initialOffsetY = { 0 },
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessMedium
                )
            ),
            exit = fadeOut() + slideOutVertically(
                targetOffsetY = { 0 },
                animationSpec = tween(300)
            ),
            modifier = Modifier
                .align(Alignment.TopCenter)
                .offset(y = (-55).dp)
        ) {
            Text(
                text = formatTime(recordingTime),
                color = MaterialTheme.colorScheme.error,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .background(
                        MaterialTheme.colorScheme.surfaceContainer,
                        RoundedCornerShape(12.dp)
                    )
                    .padding(horizontal = 12.dp, vertical = 6.dp)
            )
        }

        if (isRecording) {
            repeat(3) { index ->
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .scale(waveScale - index * 0.4f)
                        .clip(CircleShape)
                        .background(
                            MaterialTheme.colorScheme.error.copy(
                                alpha = ((waveAlpha - index * 0.15f).coerceAtLeast(0f))
                            )
                        )
                )
            }
        }

        Box(
            modifier = Modifier
                .size(40.dp)
                .scale(if (isRecording) scale * pulseScale else scale)
                .clip(CircleShape)
                .background(
                    if (isUploading)
                        MaterialTheme.colorScheme.secondary
                    else if (isRecording)
                        MaterialTheme.colorScheme.error
                    else
                        MaterialTheme.colorScheme.primary
                )
                .pointerInput(Unit) {
                    detectTapGestures(
                        onLongPress = {
                            if (!isUploading) {
                                haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                                isLocked = false
                                coroutineScope.launch {
                                    mainViewModel.startRecording()
                                }
                            }
                        },
                        onPress = {
                            if (!isUploading) {
                                val pressed = tryAwaitRelease()
                                if (isRecording && !isLocked) {
                                    coroutineScope.launch {
                                        mainViewModel.stopRecordingAndUpload()
                                    }
                                }
                            }
                        },
                        onTap = {
                            if (!isUploading) {
                                if (!isRecording) {
                                    haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                                    isLocked = true
                                    coroutineScope.launch {
                                        mainViewModel.startRecording()
                                    }
                                } else {
                                    coroutineScope.launch {
                                        mainViewModel.stopRecordingAndUpload()
                                    }
                                    isLocked = false
                                }
                            }
                        }
                    )
                },
            contentAlignment = Alignment.Center
        ) {
            if (isUploading) {
                CircularProgressIndicator(
                    color = MaterialTheme.colorScheme.onPrimary,
                    strokeWidth = 2.dp,
                    modifier = Modifier.size(20.dp)
                )
            } else {
                Icon(
                    painter = painterResource(R.drawable.icon_mic),
                    contentDescription = "Voice record",
                    tint = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}

fun formatTime(seconds: Int): String {
    val minutes = seconds / 60
    val secs = seconds % 60
    return "${minutes}:${secs.toString().padStart(2, '0')}"
}