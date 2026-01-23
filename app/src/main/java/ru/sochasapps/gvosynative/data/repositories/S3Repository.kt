package ru.sochasapps.gvosynative.data.repositories

import android.content.Context
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider
import software.amazon.awssdk.core.sync.RequestBody
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.s3.S3Client
import software.amazon.awssdk.services.s3.model.GetUrlRequest
import software.amazon.awssdk.services.s3.model.PutObjectRequest
import java.io.File
import java.net.URI
import ru.sochasapps.gvosynative.BuildConfig
import software.amazon.awssdk.http.urlconnection.UrlConnectionHttpClient
import java.util.Properties

class S3Repository(
    private val context: Context,

    ) {
    private val credentials: S3Credentials by lazy { ConfigReader.getS3Credentials(context) }
    private val s3Client: S3Client by lazy {
        createS3Client()
    }

    private fun createS3Client(): S3Client {
        val awsCredentials = AwsBasicCredentials.create(
            credentials.accessKey,
            credentials.secretKey
        )

        return S3Client.builder()
            .endpointOverride(URI.create(credentials.endpoint))
            .credentialsProvider(StaticCredentialsProvider.create(awsCredentials))
            .region(Region.of(credentials.region))
            .httpClientBuilder(UrlConnectionHttpClient.builder())
            .build()
    }

    suspend fun uploadAudioFile(
        audioFile: File,
    ): Result<String> = withContext(Dispatchers.IO) {
        val bucketName by lazy { ConfigReader.getS3Bucket(context) }
        try {

            val objectKey = "audio/${System.currentTimeMillis()}_${audioFile.name}"

            val request = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(objectKey)
                .contentType("audio/m4a")
                .build()

            s3Client.putObject(request, RequestBody.fromFile(audioFile))

            val urlRequest = GetUrlRequest.builder()
                .bucket(bucketName)
                .key(objectKey)
                .build()

            val url = s3Client.utilities().getUrl(urlRequest).toString()
            Log.i("RRRR", "URL: $url")
            Result.success(url)
        } catch (e: Exception) {
            e.message?.let { Log.i("RRRR", it) };
            Result.failure(e)
        }
    }
}

data class S3Credentials(
    val accessKey: String,
    val secretKey: String,
    val endpoint: String,
    val region: String
)

object ConfigReader {

    fun getS3Credentials(context: Context): S3Credentials {
        return S3Credentials(
            accessKey = BuildConfig.AWS_ACCESS_KEY,
            secretKey = BuildConfig.AWS_SECRET_KEY,
            endpoint = BuildConfig.S3_ENDPOINT,
            region = BuildConfig.S3_REGION
        )
    }

    fun getS3Bucket(context: Context): String {
        return BuildConfig.S3_BUCKET
    }
}