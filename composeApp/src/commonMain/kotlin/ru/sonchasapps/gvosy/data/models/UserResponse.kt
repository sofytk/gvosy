package ru.sonchasapps.gvosy.data.models

import io.ktor.http.ContentType.MultiPart.Encrypted
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserResponse(
    @SerialName("name")
    val userName: String,
    @SerialName("token")
    val userToken: String?,
    @SerialName("isPremium")
    val isPremium: Boolean
){
    fun toEntity(): UserEntity {
        return UserEntity(
            userName = this.userName,
            userToken = userToken,
            isPremium = this.isPremium,
        )
    }
}

