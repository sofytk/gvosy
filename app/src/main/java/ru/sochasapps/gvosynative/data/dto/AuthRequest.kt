package ru.sochasapps.gvosynative.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AuthRequest(
    @SerialName("name")
    val userName : String,
    @SerialName("email")
    val email : String,
    @SerialName("password")
    val password : String
)