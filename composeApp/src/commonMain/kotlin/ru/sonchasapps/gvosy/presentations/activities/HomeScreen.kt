package ru.sonchasapps.gvosy.presentations.activities

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.autofill.ContentDataType.Companion.Date
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavHostController
import gvosy.composeapp.generated.resources.Res
import gvosy.composeapp.generated.resources.avatar_dove
import gvosy.composeapp.generated.resources.icon_add
import gvosy.composeapp.generated.resources.icon_camera
import gvosy.composeapp.generated.resources.icon_mic
import gvosy.composeapp.generated.resources.icon_notes
import gvosy.composeapp.generated.resources.icon_settings
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import ru.sonchasapps.gvosy.data.models.ActionType
import ru.sonchasapps.gvosy.data.models.MessageEntity
import ru.sonchasapps.gvosy.data.models.RecordedAudio
import ru.sonchasapps.gvosy.presentations.component.MessageItem
import ru.sonchasapps.gvosy.presentations.theme.ui.theme.AppTheme
import ru.sonchasapps.gvosy.presentations.theme.ui.theme.bodyTextSize
import ru.sonchasapps.gvosy.presentations.viewModels.MainViewModel
import ru.sonchasapps.gvosy.presentations.viewModels.UserViewModel

@Preview
@Composable
fun HomeScreen(
    navController: NavHostController,
    userViewModel: UserViewModel,
    mainViewModel: MainViewModel
) {
    AppTheme() {
        var text by remember { mutableStateOf("") }
        var assistantName by remember { mutableStateOf("Jane") }
        var chatItems = mutableListOf<MessageEntity>()

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
                        painter = painterResource(Res.drawable.icon_notes),
                        contentDescription = "Notes icon",
                        modifier = Modifier
                            .size(30.dp)
                            .weight(1f)
                            .align(Alignment.CenterVertically)
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
                        contentDescription = "Settings icon",
                        modifier = Modifier.size(30.dp).weight(1f)
                    )
                }

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f)
                        .background(MaterialTheme.colorScheme.background)
                ) {
                    LazyColumn {
                        items(chatItems.size) { message ->
                            MessageItem(
                                message = chatItems[message],
                                onActionClick = { action ->
                                    when (action.type) {
//                                        ActionType.NOTE -> navigateToNote(action.targetId)
//                                        ActionType.TODO_LIST -> navigateToTodo(action.targetId)
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

                            VoiceRecordButton(mainViewModel) { audio ->
                                mainViewModel.onAudioRecorded(audio)
                            }
//                            IconButton(
//                                onClick = { /* действие для камеры */ },
//                                modifier = Modifier
//                                    .size(35.dp)
//                                    .clickable(
//                                        interactionSource = MutableInteractionSource(),
//                                        indication = null
//                                    ) { /* действие для микрофона */ },
//                                colors = IconButtonDefaults.iconButtonColors(
//                                    contentColor = MaterialTheme.colorScheme.primary,
//                                    disabledContentColor = MaterialTheme.colorScheme.onSurface.copy(
//                                        alpha = 0.12f
//                                    )
//                                )
//                            ) {
//                                Icon(
//                                    painter = painterResource(Res.drawable.icon_camera),
//                                    contentDescription = "Camera icon",
//                                    modifier = Modifier.size(24.dp)
//                                )
//                            }
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
    onRecorded: (RecordedAudio) -> Unit
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
                    if (isRecording)
                        MaterialTheme.colorScheme.error
                    else
                        MaterialTheme.colorScheme.primary
                )
                .pointerInput(Unit) {
                    detectTapGestures(
                        onLongPress = {
                            haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                            isLocked = false
                            coroutineScope.launch {
                                mainViewModel.startRecording()
                            }
                        },
                        onPress = {
                            val pressed = tryAwaitRelease()
                            if (isRecording && !isLocked) {
                                mainViewModel.stopRecording(onRecorded)
                            }
                        },
                        onTap = {
                            if (!isRecording) {
                                haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                                isLocked = true
                                coroutineScope.launch {
                                    mainViewModel.startRecording()
                                }
                            } else {
                                mainViewModel.stopRecording(onRecorded)
                                isLocked = false
                            }
                        }
                    )
                },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(Res.drawable.icon_mic),
                contentDescription = "Voice record",
                tint = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

fun formatTime(seconds: Int): String {
    val minutes = seconds / 60
    val secs = seconds % 60
    return "${minutes}:${secs.toString().padStart(2, '0')}"
}