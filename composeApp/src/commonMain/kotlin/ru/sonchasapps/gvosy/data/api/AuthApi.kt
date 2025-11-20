package ru.sonchasapps.gvosy.data.api

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import ru.sonchasapps.gvosy.data.models.AuthRequest
import ru.sonchasapps.gvosy.data.models.AuthResponse
import ru.sonchasapps.gvosy.data.models.LogInRequest

class AuthApi(private val client: HttpClient) {
    private val domain = "http://192.168.50.132:8081"

    suspend fun register(request: AuthRequest): AuthResponse {
        println("AuthApi: POST /api/auth/register body=$request")
        val response = client.post("$domain/api/auth/register") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }
        println("AuthApi: response status=${response.status}")
        return response.body()
    }

    suspend fun login(request: LogInRequest): AuthResponse {
        println("AuthApi: POST /api/auth/login body=$request")
        val response = client.post("$domain/api/auth/login") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }
        println("AuthApi: response status=${response.status}")
        return response.body()
    }
}