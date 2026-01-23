package ru.sochasapps.gvosynative.data.config

import kotlinx.serialization.Serializable

@Serializable
data class YandexStorageConfig(
    val accessKey: String = "",
    val secretKey: String = "",
    val bucketName: String = "",
    val region: String = "ru-central1",
    val endpoint: String = "https://storage.yandexcloud.net",
    val audioFolder: String = "audio"
) {
    companion object {
        fun testConfig() = YandexStorageConfig(
            accessKey = "YCAJEWXXXXXXXXXXXXXXXX",
            secretKey = "YCPsXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX",
            bucketName = "your-bucket-name",
            region = "ru-central1",
            endpoint = "https://storage.yandexcloud.net",
            audioFolder = "audio"
        )
    }
}