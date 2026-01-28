package ru.sochasapps.gvosynative.data.repositories

import java.io.File

interface VoiceMessageRepository {
    suspend fun uploadAudioToStorage(audioFile: File): Result<String>
    suspend fun sendAudioMetadataToServer(audioUrl: String): Result<String>
}