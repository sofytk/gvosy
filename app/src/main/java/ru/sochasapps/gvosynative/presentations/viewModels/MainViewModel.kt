package ru.sochasapps.gvosynative.presentations.viewModels

import androidx.lifecycle.ViewModel

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import ru.sochasapps.gvosynative.data.repositories.VoiceMessageRepository
import ru.sochasapps.gvosynative.domain.service.VoiceRecorder

class MainViewModel(
    private val voiceRecorder: VoiceRecorder,
    private val voiceMessageRepository: VoiceMessageRepository
) : ViewModel() {

    private val _isRecording = MutableStateFlow(false)
    val isRecording = _isRecording.asStateFlow()

    private val _uploadState = MutableStateFlow<UploadState>(UploadState.Idle)
    val uploadState = _uploadState.asStateFlow()

    suspend fun startRecording() {
        voiceRecorder.startRecording()
        _isRecording.value = true
    }

    suspend fun stopRecordingAndUpload() {
        val recordedAudio = voiceRecorder.stopRecording()
        _isRecording.value = false
        if (recordedAudio != null) {
            _uploadState.value = UploadState.Uploading(0f)
            val uploadResult = voiceMessageRepository.uploadAudioToStorage(
                recordedAudio.file
            )
            uploadResult.fold(
                onSuccess = { audioUrl ->
                    voiceMessageRepository.sendAudioMetadataToServer(
                        audioUrl = audioUrl
                    )
                    _uploadState.value = UploadState.Success(audioUrl)
                },
                onFailure = { exception ->
                    _uploadState.value = UploadState.Error(
                        exception.message ?: "Unknown error"
                    )
                }
            )
        }
    }

    fun resetUploadState() {
        _uploadState.value = UploadState.Idle
    }

    sealed class UploadState {
        object Idle : UploadState()
        data class Uploading(val progress: Float) : UploadState()
        data class Success(val audioUrl: String) : UploadState()
        data class Error(val message: String) : UploadState()
    }

   
}