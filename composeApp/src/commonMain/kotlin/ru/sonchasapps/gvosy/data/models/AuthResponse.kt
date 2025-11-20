package ru.sonchasapps.gvosy.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AuthResponse (
    @SerialName("accessToken")
    val accessToken : String,
    @SerialName("tokenType")
    val tokenType : String,
    @SerialName("userData")
    val userData : UserResponse
)