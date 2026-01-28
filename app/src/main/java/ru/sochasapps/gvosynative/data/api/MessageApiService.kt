package ru.sochasapps.gvosynative.data.api

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import ru.sochasapps.gvosynative.data.dto.CreateNoteRequest
import ru.sochasapps.gvosynative.data.dto.CreateTodoRequest
import ru.sochasapps.gvosynative.data.dto.MessageDto
import ru.sochasapps.gvosynative.data.dto.NoteResponse
import ru.sochasapps.gvosynative.data.dto.TextMessageRequest
import ru.sochasapps.gvosynative.data.dto.TodoResponse

class MessageApiService(
    private val client: HttpClient,
    private val baseUrl: String = "http://192.168.31.251:8080"
) {

    suspend fun sendVoiceMessage(
        token: String?,
        audioUrl: String,
        conversationId: String?
    ): MessageDto {
        return client.post("$baseUrl/api/messages/voice_message") {
            header("Authorization", "Bearer $token")
            parameter("audioUrl", audioUrl)
            conversationId?.let { parameter("conversationId", it) }
        }.body()
    }

    suspend fun sendTextMessage(
        token : String?,
        text: String,
        conversationId: String?
    ): MessageDto {
        return client.post("$baseUrl/api/messages/text_message") {
            header("Authorization", "Bearer $token")
            contentType(ContentType.Application.Json)
            setBody(TextMessageRequest(text, conversationId))
        }.body()
    }

    suspend fun getConversation(
        token : String?,
        conversationId: String
    ): List<MessageDto> {
        return client.get("$baseUrl/api/messages/conversation/$conversationId") {
            header("Authorization", "Bearer $token")
        }.body()
    }

    suspend fun getUserMessages(token : String?): List<MessageDto> {
        return client.get("$baseUrl/api/messages/user") {
            header("Authorization",  "Bearer $token")
        }.body()
    }

    suspend fun createNoteFromMessage(
        token : String?,
        messageId: String,
        request: CreateNoteRequest
    ): NoteResponse {
        return client.post("$baseUrl/api/messages/$messageId/create-note") {
            header("Authorization", "Bearer $token")
            contentType(ContentType.Application.Json)
            setBody(request)
        }.body()
    }
    suspend fun createTodoFromMessage(
        token : String?,
        messageId: String,
        request: CreateTodoRequest
    ): TodoResponse {
        return client.post("$baseUrl/api/messages/$messageId/create-todo") {
            header("Authorization", "Bearer $token")
            contentType(ContentType.Application.Json)
            setBody(request)
        }.body()
    }
}