package ru.sonchasapps.gvosy

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
