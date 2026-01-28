package ru.sochasapps.gvosynative.data.dto

import kotlinx.serialization.Serializable


@Serializable
data class NoteResponse(
    val id: String,
    val title: String,
    val content: String,
    val createdAt: Long
)
