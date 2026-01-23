package ru.sochasapps.gvosynative.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AssistantRequest (
    @SerialName("assistantName")
    val assistantName : String,
    @SerialName("assistantAge")
    val assistantAge : Int,
    @SerialName("assistantSex")
    val assistantSex : Boolean,
    @SerialName("assistantDescription")
    val assistantDescription : String?,
    val token : String,
)