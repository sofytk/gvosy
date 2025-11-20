package ru.sonchasapps.gvosy.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "assistant_data")
data class AssistantEntity(
    @PrimaryKey(autoGenerate = true) val id : Long= 0L,
    val assistantName : String,
    val assistantAge : Int,
    val assistantSex : String,
    val assistantDescription : String?,
    val userId : Long,
    val assistantMessagesId: String?,
    val assistantImg : String?,
    var assistantMessageLimit : Int,

)