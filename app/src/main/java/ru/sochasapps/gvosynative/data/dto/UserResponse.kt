package ru.sochasapps.gvosynative.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.sochasapps.gvosynative.data.models.UserEntity

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