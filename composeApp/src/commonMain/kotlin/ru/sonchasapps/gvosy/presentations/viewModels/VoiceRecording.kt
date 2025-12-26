package ru.sonchasapps.gvosy.presentations.viewModels

import ru.sonchasapps.gvosy.data.models.RecordedAudio

expect class VoiceRecorder {
    suspend fun startRecording()
    fun stopRecording(): RecordedAudio?
}