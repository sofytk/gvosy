package ru.sochasapps.gvosynative.data.models

import java.io.File

data class RecordedAudio(
    val file: File,
    val durationMs: Long
)