package ru.sonchasapps.gvosy.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserResponse(
    @SerialName("name")
    val userName: String,
    @SerialName("email")
    val userEmail: String?,
    @SerialName("token")
    val userToken: String?,
    @SerialName("isPremium")
    val isPremium: Boolean
){
    fun toEntity(): UserEntity {
        return UserEntity(
            userName = this.userName,
            userEmail = this.userEmail,
            userToken = this.userToken,
            isPremium = this.isPremium,
        )
    }
}

