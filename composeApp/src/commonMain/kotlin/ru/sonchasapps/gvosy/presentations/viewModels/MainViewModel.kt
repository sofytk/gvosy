package ru.sonchasapps.gvosy.presentations.viewModels

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.withContext
import ru.sonchasapps.gvosy.data.models.RecordedAudio

class MainViewModel ( private val recorder: VoiceRecorder ) : ViewModel() {

    private val _isRecording = MutableStateFlow(false)
    val isRecording = _isRecording.asStateFlow()

    suspend fun startRecording() {
           recorder.startRecording()
           _isRecording.value = true
    }

    fun stopRecording(
        onResult: (RecordedAudio) -> Unit
    ) {
        recorder.stopRecording()?.let {
            onResult(it)
        }
        _isRecording.value = false
    }

    fun onAudioRecorded(audio: RecordedAudio) {
        println("onAudioRecorded audio: ${audio.path} ${audio.durationMs}")
    }
}