package ru.sonchasapps.gvosy.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    val userName: String,
    val usersAssistantId: Long,
    val usersMessagesId: Long,
)