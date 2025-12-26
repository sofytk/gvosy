package ru.sonchasapps.gvosy.presentations.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import gvosy.composeapp.generated.resources.Res
import gvosy.composeapp.generated.resources.icon_mic
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import ru.sonchasapps.gvosy.data.models.AssistantAction
import ru.sonchasapps.gvosy.data.models.MessageEntity

@Composable
@Preview
fun MessageItem(message: MessageEntity, onActionClick: (AssistantAction) -> Unit) {
    val isUser = message is MessageEntity.UserText || message is MessageEntity.UserVoice

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp),
        horizontalAlignment = if (isUser) Alignment.End else Alignment.Start
    ) {
        Surface(
            shape = RoundedCornerShape(12.dp),
            color = if (isUser) MaterialTheme.colorScheme.primaryContainer
            else MaterialTheme.colorScheme.secondaryContainer,
            modifier = Modifier.widthIn(max = 300.dp)
        ) {
            Column(modifier = Modifier.padding(12.dp)) {
                when (message) {
                    is MessageEntity.UserText -> Text(text = message.text)

                    is MessageEntity.UserVoice -> Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            painter = painterResource(Res.drawable.icon_mic),
                            contentDescription = "Voice icon",
                            modifier = Modifier.size(20.dp)
                        )
                        Text("Голосовое сообщение (${message.durationSec}с)")
                    }

                    is MessageEntity.AssistantResponse -> {
                        Text(text = message.text)
                        message.action?.let { action ->
                            Spacer(modifier = Modifier.height(8.dp))
                            Button(
                                onClick = { onActionClick(action) },
                                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp)
                            ) {
                                Icon(
                                    painter = painterResource(Res.drawable.icon_mic),
                                    contentDescription = "Voice icon",
                                    modifier = Modifier.size(20.dp)
                                )
                                Spacer(Modifier.width(8.dp))
                                Text(action.label)
                            }
                        }
                    }
                }
            }
        }
    }
}