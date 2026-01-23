package ru.sochasapps.gvosynative.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LogInRequest(
  @SerialName("email")
    val email : String,
  @SerialName("password")
  val password : String,
)