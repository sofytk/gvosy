package ru.sonchasapps.gvosy

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import ru.sonchasapps.gvosy.activities.App

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Gvosy",
    ) {
        App()
    }
}