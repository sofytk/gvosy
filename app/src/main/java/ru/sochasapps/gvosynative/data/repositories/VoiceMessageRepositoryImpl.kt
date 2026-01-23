package ru.sochasapps.gvosynative.data.repositories

import java.io.File

class VoiceMessageRepositoryImpl(
    private val s3Repository: S3Repository,
    //private val apiService: YourApiService
) : VoiceMessageRepository {

    override suspend fun uploadAudioToStorage(audioFile: File): Result<String> {
        return s3Repository.uploadAudioFile(audioFile)
    }

    override suspend fun sendAudioMetadataToServer(
        audioUrl: String
    ){
        try {
            //apiService.sendAudioData(audioUrl)
        } catch (e: Exception) {
        }
    }
}