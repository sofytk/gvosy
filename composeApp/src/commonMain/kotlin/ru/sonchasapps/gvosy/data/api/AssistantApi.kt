package ru.sonchasapps.gvosy.data.api

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.HttpRequest
import io.ktor.client.request.accept
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.auth.parseAuthorizationHeader
import io.ktor.http.contentType
import ru.sonchasapps.gvosy.data.models.AssistantEntity
import ru.sonchasapps.gvosy.data.models.AssistantRequest
import ru.sonchasapps.gvosy.data.models.AssistantResponse

class AssistantApi(private val client: HttpClient) {
    private val domain = "http://192.168.31.251:8080"

    suspend fun add(request: AssistantRequest) : AssistantResponse {
        val response = client.post("$domain/api/assistant/add"){
            contentType(ContentType.Application.Json)
            header(HttpHeaders.Authorization, "Bearer ${request.token}")
            setBody(request)
        }
        return response.body()
    }
    suspend fun delete(request: AssistantRequest){
        val response = client.post("$domain/api/assistant/delete"){
            contentType(ContentType.Application.Json)
            header("Authorization", "Bearer ${request.token}")
            setBody(request)
        }
    }
//    suspend fun getAssistantData(/*request: UserRequest*/) : /*AssistantResponse*/ {
////        val request = " "
////        val response = client.post("$domain/api/assistant/getData"){
////            contentType(ContentType.Application.Json)
////            parseAuthorizationHeader("Bearer ${request.token}")
////            setBody(request)
////        }
////        return response.body()
//    }
}