package ru.sochasapps.gvosynative.data.dto

import kotlinx.serialization.Serializable


@Serializable
data class CreateTodoRequest(
    val title: String,
    val items: List<TodoItemRequest>
)
