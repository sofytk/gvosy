package ru.sochasapps.gvosynative.data.dto

import kotlinx.serialization.Serializable


@Serializable
data class TodoItemRequest(
    val text: String,
    val completed: Boolean = false
)
