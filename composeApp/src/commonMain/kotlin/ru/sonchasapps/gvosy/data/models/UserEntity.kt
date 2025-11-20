package ru.sonchasapps.gvosy.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

@Serializable
@Entity(tableName = "users_database")
data class UserEntity(
    @PrimaryKey(true)
    val id: Long = 0,
    @SerialName("name")
    val userName: String,
    @SerialName("email")
    val userEmail: String?,
    @SerialName("token")
    val userToken: String?,
//    @SerialName("assistant_data")
//    val usersAssistantId: AssistantEntity?,
    @SerialName("isPremium")
    var isPremium: Boolean
)