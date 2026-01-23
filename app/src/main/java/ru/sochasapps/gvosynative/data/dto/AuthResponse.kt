package ru.sochasapps.gvosynative.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.sochasapps.gvosynative.data.dto.UserResponse

@Serializable
data class AuthResponse (
    @SerialName("accessToken")
    val accessToken : String,
    @SerialName("refreshToken")
    val refreshToken : String,
    @SerialName("tokenType")
    val tokenType : String,
    @SerialName("userData")
    val userData : UserResponse
)