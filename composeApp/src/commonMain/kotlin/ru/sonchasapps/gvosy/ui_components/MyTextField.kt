package ru.sonchasapps.gvosy.ui_components

import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import ru.sonchasapps.gvosy.theme.ui.theme.cornerRadiusTextField

@Composable
fun MyTextField(hint : String, brushEnable : Boolean, width : Float, keyBoard : KeyboardType){
    var assistantName by rememberSaveable { mutableStateOf("") }
    var textStyle : TextStyle
    if (brushEnable) {
         textStyle = TextStyle(
            brush = Brush.linearGradient(
                colors = listOf(
                    MaterialTheme.colorScheme.primary,
                    MaterialTheme.colorScheme.onBackground,
                ),
                start = Offset(0f, 5f),
                end = Offset.Infinite
            )
        )
    }else{
        textStyle = TextStyle(background = MaterialTheme.colorScheme.onPrimary)
    }
    OutlinedTextField(
        value = assistantName,
        onValueChange = { assistantName = it },
        modifier = Modifier.width((width / 3).dp),
        placeholder = { Text(hint) },
        textStyle = textStyle,
        keyboardOptions = KeyboardOptions(keyboardType = keyBoard),
        shape = RoundedCornerShape(cornerRadiusTextField)
    )
}