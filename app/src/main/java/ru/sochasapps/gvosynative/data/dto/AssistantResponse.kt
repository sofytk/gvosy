package ru.sochasapps.gvosynative.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.sochasapps.gvosynative.data.models.AssistantEntity

@Serializable
data class AssistantResponse(
    @SerialName("name")
    val assistantName : String,
    @SerialName("age")
    val assistantAge : Int,
    @SerialName("sex")
    val assistantSex : Boolean,
    @SerialName("description")
    val assistantDescription : String?,
    @SerialName("image")
    val assistantImg : String?,
){
    fun toEntity() : AssistantEntity {
        return AssistantEntity(
            assistantName = assistantName,
            assistantAge = assistantAge,
            assistantSex = assistantSex,
            assistantDescription = assistantDescription,
            assistantImg = assistantImg,
        )
    }
}