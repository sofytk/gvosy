package ru.sochasapps.gvosynative.data.dto

import kotlinx.serialization.Serializable


@Serializable
data class TodoResponse(
    val id: String,
    val title: String,
    val items: List<TodoItemResponse>,
    val createdAt: Long
)
