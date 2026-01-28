package ru.sochasapps.gvosynative.presentations.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import ru.sochasapps.gvosynative.AppPreferences
import ru.sochasapps.gvosynative.data.api.AssistantApi
import ru.sochasapps.gvosynative.data.api.MessageApiService
import ru.sochasapps.gvosynative.data.dto.CreateNoteRequest
import ru.sochasapps.gvosynative.data.dto.CreateTodoRequest
import ru.sochasapps.gvosynative.data.dto.MessageDto
import ru.sochasapps.gvosynative.data.dto.MessageRole
import ru.sochasapps.gvosynative.data.dto.TodoItemRequest
import ru.sochasapps.gvosynative.data.models.ActionType
import ru.sochasapps.gvosynative.data.models.AssistantAction
import ru.sochasapps.gvosynative.data.models.MessageEntity
import ru.sochasapps.gvosynative.data.repositories.UserRepository
import ru.sochasapps.gvosynative.data.repositories.VoiceMessageRepository
import ru.sochasapps.gvosynative.data.websocket.WebSocketEvent
import ru.sochasapps.gvosynative.data.websocket.WebSocketService
import java.util.*

class ChatViewModel(
    private val webSocketService: WebSocketService,
    private val apiService: AssistantApi,
    private val userRepository: UserRepository,
    private val messageApiService: MessageApiService,
    private val voiceMessageRepository: VoiceMessageRepository,
    private val appPreferences: AppPreferences
) : ViewModel() {

    private val _chatMessages = MutableStateFlow<List<MessageEntity>>(emptyList())
    val chatMessages: StateFlow<List<MessageEntity>> = _chatMessages.asStateFlow()

    private val _connectionStatus = MutableStateFlow(false)
    val connectionStatus: StateFlow<Boolean> = _connectionStatus.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _currentConversationId = MutableStateFlow<String?>(null)
    val currentConversationId: StateFlow<String?> = _currentConversationId.asStateFlow()

    init {
        setupWebSocket()
        initializeConversation()
    }

    private fun setupWebSocket() {
        webSocketService.connect()
        viewModelScope.launch {
            webSocketService.events.collect { event ->
                when (event) {
                    is WebSocketEvent.Connected -> {
                        Log.i("RRRR", "WebSocket connected")
                        _connectionStatus.value = true
                    }

                    is WebSocketEvent.MessageReceived -> {
                        Log.i("RRRR", "WebSocket messageReceived")
                        handleIncomingMessage(event.message)
                    }

                    is WebSocketEvent.Error -> {
                        Log.i("RRRR", "WebSocket error")
                        _connectionStatus.value = false
                    }

                    is WebSocketEvent.Disconnected -> {
                        Log.i("RRRR", "WebSocket disconnected")
                        _connectionStatus.value = false
                    }

                    is WebSocketEvent.Reconnecting -> {
                    }

                    else -> {}
                }
            }
        }
    }

    private fun handleIncomingMessage(messageDTO: MessageDto) {
        Log.i("RRRR", "MESSAGE: ${messageDTO.content}")
        val messageEntity = when (messageDTO.role) {
            MessageRole.USER -> {
                if (messageDTO.audioUrl != null) {
                    MessageEntity.UserVoice(
                        id = messageDTO.id,
                        audioUrl = messageDTO.audioUrl,
                        durationSec = 0,
                        transcription = messageDTO.content
                    )
                } else {
                    MessageEntity.UserText(
                        id = messageDTO.id,
                        text = messageDTO.content
                    )
                }
            }

            MessageRole.ASSISTANT -> {
                val action = parseActionFromText(messageDTO.content)
                MessageEntity.AssistantResponse(
                    id = messageDTO.id,
                    text = messageDTO.content,
                    action = action
                )
            }
        }

        _chatMessages.update { currentMessages ->
            currentMessages + messageEntity
        }
    }

    private fun parseActionFromText(text: String): AssistantAction? {
        return when {
            text.contains("заметк") -> AssistantAction(
                type = ActionType.NOTE,
                label = "Создать заметку",
                targetId = null
            )
            text.contains("список") || text.contains("задач") -> AssistantAction(
                type = ActionType.TODO_LIST,
                label = "Создать список задач",
                targetId = null
            )
            else -> null
        }
    }

    private fun loadPreviousMessages() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val user = userRepository.getCurrentUser()
                if (user != null) {
                    currentConversationId?.let { conversationId ->
                        val messages = conversationId.value?.let {
                            messageApiService.getConversation(
                                token = user.userToken,
                                conversationId = it
                            )
                        }

                        val messageEntities = messages?.map { dto ->
                            convertDtoToEntity(dto)
                        }

                        _chatMessages.value = messageEntities!!
                    } ?: run {

                        val messages = messageApiService.getUserMessages( token = user.userToken)
                        val messageEntities = messages.map { dto ->
                            convertDtoToEntity(dto)
                        }
                        _chatMessages.value = messageEntities
                    }
                }
            } catch (e: Exception) {
            } finally {
                _isLoading.value = false
            }
        }
    }

    private fun convertDtoToEntity(dto: MessageDto): MessageEntity {
        return when (dto.role) {
            MessageRole.USER -> {
                if (dto.audioUrl != null) {
                    MessageEntity.UserVoice(
                        id = dto.id,
                        audioUrl = dto.audioUrl,
                        durationSec = 0,
                        transcription = dto.content
                    )
                } else {
                    MessageEntity.UserText(
                        id = dto.id,
                        text = dto.content
                    )
                }
            }
            MessageRole.ASSISTANT -> MessageEntity.AssistantResponse(
                id = dto.id,
                text = dto.content,
                action = parseActionFromText(dto.content)
            )
        }
    }

    fun onActionClick(action: AssistantAction) {
        viewModelScope.launch {
            _isLoading.value = true

            try {
                val user = userRepository.getCurrentUser()
                if (user != null) {
                    when (action.type) {
                        ActionType.NOTE -> {
                            val lastAssistantMessage = _chatMessages.value.lastOrNull {
                                it is MessageEntity.AssistantResponse
                            } as? MessageEntity.AssistantResponse

                            lastAssistantMessage?.let { message ->
                                try {
                                    val response = messageApiService.createNoteFromMessage(
                                        token = user.userToken,
                                        messageId = message.id,
                                        request = CreateNoteRequest(
                                            title = "Заметка из сообщения",
                                            content = message.text
                                        )
                                    )

                                    val noteMessage = MessageEntity.AssistantResponse(
                                        id = UUID.randomUUID().toString(),
                                        text = "Заметка создана: ${response.title}",
                                        action = null
                                    )
                                    _chatMessages.update { currentMessages ->
                                        currentMessages + noteMessage
                                    }
                                } catch (e: Exception) {
                                    showErrorMessage("Ошибка создания заметки: ${e.message}")
                                }
                            }
                        }

                        ActionType.TODO_LIST -> {
                            val lastAssistantMessage = _chatMessages.value.lastOrNull {
                                it is MessageEntity.AssistantResponse
                            } as? MessageEntity.AssistantResponse

                            lastAssistantMessage?.let { message ->
                                try {
                                    val response = messageApiService.createTodoFromMessage(
                                        token = user.userToken,
                                        messageId = message.id,
                                        request = CreateTodoRequest(
                                            title = "Список задач",
                                            items = parseTodoItemsFromText(message.text)
                                        )
                                    )
                                    val todoMessage = MessageEntity.AssistantResponse(
                                        id = UUID.randomUUID().toString(),
                                        text = "Список задач создан: ${response.title}",
                                        action = null
                                    )
                                    _chatMessages.update { currentMessages ->
                                        currentMessages
                                    }
                                } catch (e: Exception) {
                                    showErrorMessage("Ошибка создания списка: ${e.message}")
                                }
                            }
                        }

                        ActionType.REMINDER -> {
                        }
                    }
                } else {
                    showErrorMessage("Пользователь не авторизован")
                }
            } catch (e: Exception) {
                showErrorMessage("Ошибка: ${e.message}")
            } finally {
                _isLoading.value = false
            }
        }
    }

    private fun parseTodoItemsFromText(text: String): List<TodoItemRequest> {
        // Парсим пункты из текста
        // Пример: "1. Купить молоко\n2. Позвонить маме"
        val lines = text.split("\n")
        return lines.mapNotNull { line ->
            val trimmed = line.trim()
            if (trimmed.matches(Regex("^\\d+\\.\\s+.+"))) {
                val itemText = trimmed.substring(trimmed.indexOf('.') + 1).trim()
                TodoItemRequest(text = itemText)
            } else {
                null
            }
        }
    }

    private fun showErrorMessage(text: String) {
        viewModelScope.launch {
            val errorMessage = MessageEntity.AssistantResponse(
                id = UUID.randomUUID().toString(),
                text = text,
                action = null
            )
            _chatMessages.update { currentMessages ->
                currentMessages + errorMessage
            }
        }
    }

    private fun handleApiError(e: Exception) {
        viewModelScope.launch {
            val errorMessage = MessageEntity.AssistantResponse(
                id = UUID.randomUUID().toString(),
                text = when {
                    e.message?.contains("401") == true ||
                            e.message?.contains("403") == true ->
                        "Ошибка авторизации. Пожалуйста, войдите снова."
                    else -> "Ошибка: ${e.message ?: "Неизвестная ошибка"}"
                },
                action = null
            )
            _chatMessages.update { currentMessages ->
                currentMessages + errorMessage
            }

        }
    }

    override fun onCleared() {
        super.onCleared()
        webSocketService.disconnect()
    }


    private fun initializeConversation() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val conversationId = appPreferences.getOrCreateConversationId()
                _currentConversationId.value = conversationId
                loadConversationMessages(conversationId)
            } catch (e: Exception) {
                createNewConversation()
            } finally {
                _isLoading.value = false
            }
        }
    }

    suspend fun createNewConversation(): String {
        val newId = appPreferences.getOrCreateConversationId()
        _currentConversationId.value = newId
        _chatMessages.value = emptyList()
        return newId
    }

    fun startNewConversation() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                createNewConversation()
            } catch (e: Exception) {
            } finally {
                _isLoading.value = false
            }
        }
    }

    private suspend fun loadConversationMessages(conversationId: String) {
        try {
            val messages = messageApiService.getConversation(
                token = userRepository.getCurrentUser()?.userToken,
                conversationId = conversationId
            )
            val messageEntities = messages.map { dto ->
                convertDtoToEntity(dto)
            }
            _chatMessages.value = messageEntities
        } catch (e: Exception) {
            if (e.message?.contains("404") == true) {
                createNewConversation()
            }
        }
    }

    fun sendVoiceMessage(audioUrl: String) {
        viewModelScope.launch {
            _isLoading.value = true

            try {
                val conversationId = _currentConversationId.value
                    ?: appPreferences.getOrCreateConversationId()

                val tempId = UUID.randomUUID().toString()
                val userMessage = MessageEntity.UserVoice(
                    id = tempId,
                    audioUrl = audioUrl,
                    durationSec = 0
                )

                _chatMessages.update { currentMessages ->
                    currentMessages + userMessage
                }

                val response = messageApiService.sendVoiceMessage(
                    token = userRepository.getCurrentUser()?.userToken,
                    audioUrl = audioUrl,
                    conversationId = conversationId
                )

                _chatMessages.update { messages ->
                    messages.map {
                        if (it.id == tempId) {
                            (it as MessageEntity.UserVoice).copy(id = response.id)
                        } else it
                    }
                }

            } catch (e: Exception) {
                handleApiError(e)
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun sendTextMessage(text: String) {
        viewModelScope.launch {
            _isLoading.value = true

            try {
                val conversationId = _currentConversationId.value
                    ?: appPreferences.getOrCreateConversationId()

                val tempId = UUID.randomUUID().toString()
                val userMessage = MessageEntity.UserText(
                    id = tempId,
                    text = text
                )

                _chatMessages.update { currentMessages ->
                    currentMessages + userMessage
                }

                val response = messageApiService.sendTextMessage(
                    text = text,
                    token = userRepository.getCurrentUser()?.userToken,
                    conversationId = conversationId
                )

                _chatMessages.update { messages ->
                    messages.map {
                        if (it.id == tempId) {
                            (it as MessageEntity.UserText).copy(id = response.id)
                        } else it
                    }
                }

            } catch (e: Exception) {
                handleApiError(e)
            } finally {
                _isLoading.value = false
            }
        }
    }

    suspend fun getConversationInfo(): ConversationInfo {
        val id = _currentConversationId.value ?: ""
        val messageCount = _chatMessages.value.size

        return ConversationInfo(
            id = id,
            messageCount = messageCount,
        )
    }

}

data class ConversationInfo(
    val id: String,
    val messageCount: Int,
)