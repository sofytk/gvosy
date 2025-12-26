package ru.sonchasapps.gvosy.presentations.viewModels

import android.Manifest
import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import android.media.MediaRecorder
import android.os.Environment
import androidx.core.content.ContextCompat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.sonchasapps.gvosy.data.models.RecordedAudio
import java.io.File

actual class VoiceRecorder(private val context: Context) {
    private var recorder: MediaRecorder? = null
    private lateinit var file: File
    private var startTime = 0L

    actual suspend fun startRecording() {
        val context = context

        val downloadsDir = Environment.getExternalStoragePublicDirectory(
            Environment.DIRECTORY_DOWNLOADS
        )

        val appFolder = File(downloadsDir, "Gvosy")
        if (!appFolder.exists()) {
            appFolder.mkdirs()
        }

        file = File(
            appFolder,
            "voice_${System.currentTimeMillis()}.m4a"
        )

//        file = File(
//            context.cacheDir,
//            "voice_${System.currentTimeMillis()}.m4a"
//        )
        val permission = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.RECORD_AUDIO
        ) == PackageManager.PERMISSION_GRANTED
        if(permission) {
            recorder = MediaRecorder().apply {
                withContext(Dispatchers.Main) {
                    setAudioSource(MediaRecorder.AudioSource.MIC)
                }
                setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
                setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
                setOutputFile(file.absolutePath)
                prepare()
                start()
            }

            startTime = System.currentTimeMillis()
        }else{

        }
    }

    actual fun stopRecording(): RecordedAudio? {
        return try {
            recorder?.stop()
            recorder?.release()
            RecordedAudio(
                path = file.absolutePath,
                durationMs = System.currentTimeMillis() - startTime
            )
        } catch (e: Exception) {
            println("STOP RECORDING ERROR: ${e.message}")
        } finally {
            recorder = null
        } as RecordedAudio?
    }
}
