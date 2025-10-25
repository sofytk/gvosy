package ru.sonchasapps.gvosy.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "users_database")
data class UserEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    val userName: String,
    val userEmail: String?,
    val usersAssistantId: Long,
    val usersMessagesId: Long,
    var isFreemium: Boolean,
)