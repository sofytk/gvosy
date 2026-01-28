package ru.sochasapps.gvosynative.data.dto

import kotlinx.serialization.Serializable


@Serializable
data class TodoItemResponse(
    val id: String,
    val text: String,
    val completed: Boolean,
    val order: Int
)