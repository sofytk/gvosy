package ru.sochasapps.gvosynative.data.api

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import ru.sochasapps.gvosynative.data.dto.AuthRequest
import ru.sochasapps.gvosynative.data.dto.AuthResponse
import ru.sochasapps.gvosynative.data.dto.LogInRequest

class AuthApi(private val client: HttpClient) {
    private val domain = "http://192.168.31.251:8080"

    suspend fun register(request: AuthRequest): AuthResponse {
        val response = client.post("$domain/api/auth/register") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }
        println("AuthApi: response status=${response.status}")
        return response.body()
    }

    suspend fun login(request: LogInRequest): AuthResponse {
        val response = client.post("$domain/api/auth/login") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }
        println("AuthApi: response status=${response.status}")
        return response.body()
    }
}