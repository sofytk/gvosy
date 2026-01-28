package ru.sochasapps.gvosynative.data.websocket

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import ru.sochasapps.gvosynative.data.dto.MessageDto
import java.util.UUID
import java.util.concurrent.TimeUnit
import android.util.Log
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import okhttp3.*
import okio.ByteString
import ru.sochasapps.gvosynative.data.repositories.UserRepository
import java.util.*

sealed class WebSocketEvent {
    data class Connected(val sessionId: String) : WebSocketEvent()
    data class MessageReceived(val message: MessageDto) : WebSocketEvent()
    data class Error(val throwable: Throwable) : WebSocketEvent()
    object Disconnected : WebSocketEvent()
    object Reconnecting : WebSocketEvent()
    object Connecting : WebSocketEvent()
    object Unauthorized : WebSocketEvent()
}

interface WebSocketService {
    val events: Flow<WebSocketEvent>
    fun connect()
    fun disconnect()
    suspend fun sendMessage(message: Any): Boolean
    fun isConnected(): Boolean
    fun subscribeToAssistant(assistantId: String)
}

class WebSocketServiceImpl(
    private val userRepository: UserRepository,
    private val baseUrl: String = "ws://192.168.31.251:8080"
) : WebSocketService {

    private val TAG = "WebSocketService"

    private val client = OkHttpClient.Builder()
        .pingInterval(30, TimeUnit.SECONDS)
        .retryOnConnectionFailure(true)
        .connectTimeout(10, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()

    private var webSocket: WebSocket? = null
    private var currentAssistantId: String? = null

    private val _events = MutableSharedFlow<WebSocketEvent>(
        replay = 1,
        extraBufferCapacity = 10
    )
    override val events: Flow<WebSocketEvent> = _events.asSharedFlow()

    private val gson = Gson()
    private var reconnectJob: Job? = null
    private var connectionAttempts = 0
    private val maxReconnectAttempts = 10

    private val coroutineScope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    override fun connect() {
        reconnectJob?.cancel()

        coroutineScope.launch {
            _events.tryEmit(WebSocketEvent.Connecting)
            connectInternal()
        }
    }

    private suspend fun connectInternal() {
        connectionAttempts++

        try {
            val token = userRepository.getCurrentUser()?.userToken

            if (token == null) {
                Log.w(TAG, "No auth token available for WebSocket connection")
                _events.tryEmit(WebSocketEvent.Unauthorized)
                return
            }

            val url = if (baseUrl.contains("?")) {
                "$baseUrl"
            } else {
                "$baseUrl/ws"
            }

            Log.d(TAG, "Connecting to WebSocket: $url")

            val request = Request.Builder()
                .url(url)
                .addHeader("Authorization", "Bearer $token")
                .addHeader("User-Agent", "Android-App")
                .build()

            webSocket = client.newWebSocket(request, object : WebSocketListener() {
                override fun onOpen(webSocket: WebSocket, response: Response) {
                    Log.d(TAG, "WebSocket connection established")
                    connectionAttempts = 0

                    coroutineScope.launch {
                        if (response.code == 401) {
                            _events.tryEmit(WebSocketEvent.Unauthorized)
                            disconnect()
                        } else {
                            _events.tryEmit(WebSocketEvent.Connected("session_${System.currentTimeMillis()}"))

                            currentAssistantId?.let { assistantId ->
                                subscribeToAssistantInternal(assistantId)
                            }
                        }
                    }
                }

                override fun onMessage(webSocket: WebSocket, text: String) {
                    Log.d(TAG, "Received WebSocket message: $text")
                    try {
                        val message = gson.fromJson(text, MessageDto::class.java)
                        coroutineScope.launch {
                            _events.tryEmit(WebSocketEvent.MessageReceived(message))
                        }
                    } catch (e: JsonSyntaxException) {
                        Log.e(TAG, "Error parsing JSON message: ${e.message}")
                    }
                }

                override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
                    Log.d(TAG, "Received binary message: ${bytes.size} bytes")
                }

                override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
                    Log.d(TAG, "WebSocket closing: code=$code, reason=$reason")
                    webSocket.close(code, reason)

                    coroutineScope.launch {
                        handleConnectionClosed(code, reason)
                    }
                }

                override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
                    Log.d(TAG, "WebSocket closed: code=$code, reason=$reason")
                    coroutineScope.launch {
                        _events.tryEmit(WebSocketEvent.Disconnected)
                        scheduleReconnection()
                    }
                }

                override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
                    Log.e(TAG, "WebSocket connection failed: ${t.message}", t)

                    coroutineScope.launch {
                        handleConnectionFailure(t, response)
                    }
                }
            })

        } catch (e: Exception) {
            Log.e(TAG, "Error creating WebSocket connection: ${e.message}")
            coroutineScope.launch {
                _events.tryEmit(WebSocketEvent.Error(e))
                scheduleReconnection()
            }
        }
    }

    private suspend fun handleConnectionClosed(code: Int, reason: String) {
        when (code) {
            1008, 1003 -> {
                _events.tryEmit(WebSocketEvent.Unauthorized)
                reconnectJob?.cancel()
            }
            else -> {
                _events.tryEmit(WebSocketEvent.Disconnected)
                scheduleReconnection()
            }
        }
    }

    private suspend fun handleConnectionFailure(t: Throwable, response: Response?) {
        if (response?.code == 401 || response?.code == 403) {
            _events.tryEmit(WebSocketEvent.Unauthorized)
            reconnectJob?.cancel()
        } else {
            _events.tryEmit(WebSocketEvent.Error(t))
            scheduleReconnection()
        }
    }

    private suspend fun scheduleReconnection() {
        if (connectionAttempts >= maxReconnectAttempts) {
            Log.w(TAG, "Max reconnection attempts reached ($maxReconnectAttempts)")
            return
        }

        reconnectJob?.cancel()
        reconnectJob = coroutineScope.launch {
            val delayMillis = calculateReconnectionDelay(connectionAttempts)
            Log.d(TAG, "Scheduling reconnection in ${delayMillis}ms (attempt $connectionAttempts)")

            _events.tryEmit(WebSocketEvent.Reconnecting)
            delay(delayMillis)

            try {
                val token = userRepository.getCurrentUser()?.userToken
                if (token != null) {
                    connectInternal()
                } else {
                    Log.w(TAG, "No auth token, skipping reconnection")
                    _events.tryEmit(WebSocketEvent.Unauthorized)
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error getting auth token for reconnection: ${e.message}")
            }
        }
    }

    private fun calculateReconnectionDelay(attempt: Int): Long {
        val baseDelay = 1000L
        val maxDelay = 30000L
        val delay = baseDelay * (1 shl minOf(attempt, 5))
        return minOf(delay, maxDelay)
    }

    override fun disconnect() {
        Log.d(TAG, "Disconnecting WebSocket")

        reconnectJob?.cancel()
        reconnectJob = null

        webSocket?.close(1000, "User requested disconnect")
        webSocket = null

        currentAssistantId = null
        connectionAttempts = 0
    }

    override suspend fun sendMessage(message: Any): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                if (webSocket == null) {
                    Log.w(TAG, "Cannot send message: WebSocket is not connected")
                    return@withContext false
                }

                val json = gson.toJson(message)
                val sent = webSocket?.send(json) ?: false

                if (sent) {
                    Log.d(TAG, "Message sent successfully")
                } else {
                    Log.w(TAG, "Failed to send message")
                }

                sent
            } catch (e: Exception) {
                Log.e(TAG, "Error sending WebSocket message: ${e.message}")
                false
            }
        }
    }

    override fun isConnected(): Boolean {
        return webSocket != null
    }

    override fun subscribeToAssistant(assistantId: String) {
        currentAssistantId = assistantId

        if (isConnected() && assistantId.isNotBlank()) {
            coroutineScope.launch {
                subscribeToAssistantInternal(assistantId)
            }
        }
    }

    private suspend fun subscribeToAssistantInternal(assistantId: String) {
        try {
            val subscriptionMessage = mapOf(
                "type" to "SUBSCRIBE",
                "assistantId" to assistantId,
                "timestamp" to System.currentTimeMillis()
            )

            sendMessage(subscriptionMessage)
            Log.d(TAG, "Subscribed to assistant: $assistantId")
        } catch (e: Exception) {
            Log.e(TAG, "Error subscribing to assistant: ${e.message}")
        }
    }
}