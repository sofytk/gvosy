package ru.sonchasapps.gvosy

fun main() = application {
    startKoin {
        modules(appModule)
    }
    Window(onCloseRequest = ::exitApplication) {
        App()
    }
}