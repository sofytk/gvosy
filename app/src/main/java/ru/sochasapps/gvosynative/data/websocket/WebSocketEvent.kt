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

    private val _events = MutableSharedFlow<WebSocketEvent>(
        replay = 1,
        extraBufferCapacity = 10
    )
    override val events: Flow<WebSocketEvent> = _events.asSharedFlow()

    private var webSocket: WebSocket? = null
    private val coroutineScope = CoroutineScope(Dispatchers.IO + SupervisorJob())
    private val gson = Gson()

    private val subscriptions = mutableMapOf<String, String>()
    private var isStompConnected = false
    private var pendingSubscriptions = mutableListOf<String>()

    private val client = OkHttpClient.Builder()
        .pingInterval(30, TimeUnit.SECONDS)
        .retryOnConnectionFailure(true)
        .connectTimeout(10, TimeUnit.SECONDS)
        .readTimeout(0, TimeUnit.SECONDS)
        .build()

    override fun connect() {
        coroutineScope.launch {
            _events.emit(WebSocketEvent.Connecting)
            connectInternal()
        }
    }


    private suspend fun connectInternal() {
        try {
            val token = userRepository.getCurrentUser()?.userToken
            if (token == null) {
                _events.emit(WebSocketEvent.Unauthorized)
                return
            }

            val url = "$baseUrl/ws"
            Log.d(TAG, "Connecting to STOMP WebSocket: $url")

            val request = Request.Builder()
                .url(url)
                .addHeader("Authorization", "Bearer $token")
                .build()

            webSocket = client.newWebSocket(request, object : WebSocketListener() {
                override fun onOpen(webSocket: WebSocket, response: Response) {
                    Log.d(TAG, "WebSocket opened, sending STOMP CONNECT")
                    sendStompConnect()
                }

                override fun onMessage(webSocket: WebSocket, text: String) {
                    Log.d(TAG, "Received STOMP frame:\n$text") // Показываем полный фрейм
                    handleStompFrame(text)
                }

                override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
                    Log.d(TAG, "WebSocket closing: $code - $reason")
                    webSocket.close(1000, null)
                }

                override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
                    Log.d(TAG, "WebSocket closed")
                    isStompConnected = false
                    coroutineScope.launch {
                        _events.emit(WebSocketEvent.Disconnected)
                    }
                }

                override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
                    Log.e(TAG, "WebSocket failure", t)
                    isStompConnected = false
                    coroutineScope.launch {
                        _events.emit(WebSocketEvent.Error(t))
                    }
                }
            })

        } catch (e: Exception) {
            Log.e(TAG, "Connection error", e)
            _events.emit(WebSocketEvent.Error(e))
        }
    }

    private fun sendStompConnect() {
        val connectFrame = buildString {
            append("CONNECT\n")
            append("accept-version:1.1,1.2\n")
            append("heart-beat:10000,10000\n")
            append("\n")
            append('\u0000')
        }
        webSocket?.send(connectFrame)
        Log.d(TAG, "Sent STOMP CONNECT")
    }

    private fun handleStompFrame(frame: String) {
        val lines = frame.trim().split("\n")
        val command = lines.firstOrNull() ?: return

        Log.d(TAG, "STOMP command: $command")

        when (command) {
            "CONNECTED" -> {
                Log.d(TAG, "STOMP Connected successfully")
                isStompConnected = true
                coroutineScope.launch {
                    _events.emit(WebSocketEvent.Connected("stomp_session"))

                    processPendingSubscriptions()
                }
            }
            "MESSAGE" -> {
                handleMessageFrame(frame)
            }
            "ERROR" -> {
                Log.e(TAG, "STOMP Error frame: $frame")
                coroutineScope.launch {
                    _events.emit(WebSocketEvent.Error(Exception("STOMP Error: $frame")))
                }
            }
            "RECEIPT" -> {
                Log.d(TAG, "STOMP Receipt: $frame")
            }
        }
    }

    private fun handleMessageFrame(frame: String) {
        try {
            Log.d(TAG, "Parsing MESSAGE frame:\n$frame")

            val parts = frame.split("\n\n", limit = 2)
            if (parts.size < 2) {
                Log.w(TAG, "Invalid MESSAGE frame format")
                return
            }

            val headers = parts[0].split("\n")
            val headersMap = headers.drop(1).associate { line ->
                val (key, value) = line.split(":", limit = 2)
                key to value
            }

            Log.d(TAG, "Headers: $headersMap")

            val body = parts[1].replace("\u0000", "").trim()

            if (body.isEmpty()) {
                Log.w(TAG, "Empty message body")
                return
            }

            Log.d(TAG, "Message body: $body")

            val messageDto = gson.fromJson(body, MessageDto::class.java)
            Log.d(TAG, "Parsed MessageDto: $messageDto")

            coroutineScope.launch {
                _events.emit(WebSocketEvent.MessageReceived(messageDto))
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error parsing MESSAGE frame", e)
            e.printStackTrace()
        }
    }

    override fun subscribeToAssistant(assistantId: String) {
        Log.d(TAG, "subscribeToAssistant called for: $assistantId")

        if (!isStompConnected) {
            Log.d(TAG, "STOMP not connected yet, adding to pending subscriptions")
            pendingSubscriptions.add(assistantId)
            return
        }

        subscribeToAssistantInternal(assistantId)
    }

    private fun processPendingSubscriptions() {
        Log.d(TAG, "Processing ${pendingSubscriptions.size} pending subscriptions")
        pendingSubscriptions.forEach { assistantId ->
            subscribeToAssistantInternal(assistantId)
        }
        pendingSubscriptions.clear()
    }

    private fun subscribeToAssistantInternal(assistantId: String) {
        val destination = "/topic/assistant/"
        val subscriptionId = "sub-$assistantId"

        val subscribeFrame = buildString {
            append("SUBSCRIBE\n")
            append("id:$subscriptionId\n")
            append("destination:$destination\n")
            append("ack:auto\n")
            append("\n")
            append('\u0000')
        }

        val sent = webSocket?.send(subscribeFrame) ?: false
        if (sent) {
            subscriptions[assistantId] = subscriptionId
            Log.d(TAG, "Subscribed to: $destination")
        } else {
            Log.e(TAG, "Failed to subscribe to: $destination")
        }
    }

    override fun disconnect() {
        subscriptions.forEach { (_, subId) ->
            val unsubscribeFrame = buildString {
                append("UNSUBSCRIBE\n")
                append("id:$subId\n")
                append("\n")
                append('\u0000')
            }
            webSocket?.send(unsubscribeFrame)
        }

        val disconnectFrame = "DISCONNECT\n\n\u0000"
        webSocket?.send(disconnectFrame)

        webSocket?.close(1000, "Client disconnect")
        webSocket = null
        isStompConnected = false
        subscriptions.clear()
        pendingSubscriptions.clear()
    }

    override suspend fun sendMessage(message: Any): Boolean {
        if (!isStompConnected) {
            Log.w(TAG, "Cannot send: STOMP not connected")
            return false
        }

        return try {
            val body = gson.toJson(message)
            val sendFrame = buildString {
                append("SEND\n")
                append("destination:/app/message\n")
                append("content-type:application/json\n")
                append("content-length:${body.length}\n")
                append("\n")
                append(body)
                append('\u0000')
            }

            val sent = webSocket?.send(sendFrame) ?: false
            Log.d(TAG, "Message sent: $sent")
            sent
        } catch (e: Exception) {
            Log.e(TAG, "Error sending message", e)
            false
        }
    }

    override fun isConnected(): Boolean = isStompConnected
}