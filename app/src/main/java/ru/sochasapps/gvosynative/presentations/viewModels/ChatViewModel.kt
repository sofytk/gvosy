package ru.sochasapps.gvosynative.presentations.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.GsonBuilder
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import ru.sochasapps.gvosynative.AppPreferences
import ru.sochasapps.gvosynative.data.api.AssistantApi
import ru.sochasapps.gvosynative.data.api.MessageApiService
import ru.sochasapps.gvosynative.data.dto.AssistantContent
import ru.sochasapps.gvosynative.data.dto.CreateNoteRequest
import ru.sochasapps.gvosynative.data.dto.CreateTodoRequest
import ru.sochasapps.gvosynative.data.dto.MessageDto
import ru.sochasapps.gvosynative.data.dto.MessageRole
import ru.sochasapps.gvosynative.data.dto.MessageStatus
import ru.sochasapps.gvosynative.data.dto.TodoItemRequest
import ru.sochasapps.gvosynative.data.models.ActionType
import ru.sochasapps.gvosynative.data.models.AssistantAction
import ru.sochasapps.gvosynative.data.models.MessageEntity
import ru.sochasapps.gvosynative.data.models.MessageEntity.*
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

    private val gson = GsonBuilder().create()

    init {
        setupWebSocket()
        initializeConversation()
        viewModelScope.launch {
            appPreferences.conversationIdFlow.collect { conversationId ->
                Log.d("ChatViewModel", "ConversationId changed to: $conversationId")
                _currentConversationId.value = conversationId
            }
        }
    }

    private fun setupWebSocket() {
        webSocketService.connect()

        viewModelScope.launch {
            webSocketService.events.collect { event ->
                when (event) {
                    is WebSocketEvent.Connected -> {
                        Log.d("ChatViewModel", "WebSocket connected")
                        val assistantId = "9c87d13d-9e5f-4cc5-9f35-6df1c3027e26"
                        webSocketService.subscribeToAssistant(assistantId)
                        Log.d("ChatViewModel", "Subscribed to assistant: $assistantId")
                    }
                    is WebSocketEvent.MessageReceived -> {
                        Log.d("ChatViewModel", "Message received: ${event.message}")
                        handleIncomingMessage(event.message)
                    }
                    is WebSocketEvent.Error -> {
                        Log.e("ChatViewModel", "WebSocket error", event.throwable)
                    }
                    is WebSocketEvent.Disconnected -> {
                        Log.d("ChatViewModel", "WebSocket disconnected")
                    }
                    else -> {}
                }
            }
        }
    }

    private fun handleIncomingMessage(messageDTO: MessageDto) {
        Log.i("ChatViewModel", "Received message: id=${messageDTO.id}, role=${messageDTO.role}")

        try {

            if (messageDTO.role == null) {
                Log.d("ChatViewModel", "Ignoring intermediate notification with noteId=${messageDTO.noteId}")
                return
            }
            val currentConvId = _currentConversationId.value
            if (currentConvId != null && messageDTO.conversationId != currentConvId) {
                Log.d("ChatViewModel", "Ignoring message from different conversation: ${messageDTO.conversationId} != $currentConvId")
                return
            }

            if (_chatMessages.value.any { it.id == messageDTO.id }) {
                Log.d("ChatViewModel", "Message already exists, skipping: ${messageDTO.id}")
                return
            }

            val messageEntity = when (messageDTO.role) {
                MessageRole.USER -> {
                    if (messageDTO.status == MessageStatus.PENDING) {
                        Log.d("ChatViewModel", "Ignoring PENDING user message")
                        return
                    }

                    if (!messageDTO.audioUrl.isNullOrEmpty()) {
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
                    val assistantContent = try {
                        val parsed = gson.fromJson(messageDTO.content, AssistantContent::class.java)
                        Log.d("ChatViewModel", "Parsed AssistantContent: $parsed")
                        Log.d("ChatViewModel", "assistantReply field: ${parsed?.assistantReply}")
                        Log.d("ChatViewModel", "summary field: ${parsed?.summary}")
                        parsed
                    } catch (e: Exception) {
                        Log.e("ChatViewModel", "Failed to parse assistant content", e)
                        Log.e("ChatViewModel", "Content was: ${messageDTO.content}")
                        null
                    }

                    val displayText = when {
                        !assistantContent?.assistantReply.isNullOrBlank() -> {
                            Log.d("ChatViewModel", "Using assistantReply")
                            assistantContent.assistantReply
                        }
                        !assistantContent?.summary.isNullOrBlank() -> {
                            Log.d("ChatViewModel", "Using summary as fallback")
                            assistantContent.summary
                        }
                        else -> {
                            Log.d("ChatViewModel", "Using raw content as fallback")
                            messageDTO.content
                        }
                    }

                    Log.d("ChatViewModel", "Final display text: $displayText")

                    val action = parseActionFromAssistantContent(assistantContent)

                    MessageEntity.AssistantResponse(
                        id = messageDTO.id,
                        text = displayText,
                        action = action
                    )
                }
            }

            _chatMessages.update { currentMessages ->
                currentMessages + messageEntity
            }

            Log.d("ChatViewModel", "Message added successfully: ${messageEntity.id}")

        } catch (e: Exception) {
            Log.e("ChatViewModel", "Error handling incoming message", e)
            e.printStackTrace()
        }
    }

    private fun parseActionFromAssistantContent(content: AssistantContent?): AssistantAction? {
        if (content == null) return null

            return when (content.classification) {
                "todo_list" -> {
                    val items = content.extra?.todoList?.items ?: emptyList()
                    AssistantAction(
                        type = ActionType.TODO_LIST,
                        label = "Создать список задач",
                        targetId = null
                    )
                }
                "task" -> {
                    val description = content.extra?.task?.description ?: ""
                    val date = content.extra?.task?.date
                    AssistantAction(
                        type = ActionType.TODO_LIST,
                        label = "Создать задачу",
                        targetId = null
                    )
                }
                "journal" -> {
                    AssistantAction(
                        type = ActionType.JOURNAL,
                        label = "Создать заметку",
                        targetId = null
                    )
                }
                "weekly_plan" -> {
                    val weeklyPlan = content.extra?.weeklyPlan
                    Log.d("ChatViewModel", "Weekly plan: date=${weeklyPlan?.date}, activity=${weeklyPlan?.activity}")
                    AssistantAction(
                        type = ActionType.WEEKLY_PLAN,
                        label = "Создать заметку",
                        targetId = null
                    )
                }
                "project_idea" -> {
                    AssistantAction(
                        type = ActionType.PROJECT_IDEA,
                        label = "Создать заметку",
                        targetId = null
                    )
                }
                else -> null
            }
        }


    private fun convertDtoToEntity(dto: MessageDto): MessageEntity {
        return when (dto.role) {
            MessageRole.USER -> {
                if (!dto.audioUrl.isNullOrEmpty()) {
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
            MessageRole.ASSISTANT -> {
                val assistantContent = try {
                    gson.fromJson(dto.content, AssistantContent::class.java)
                } catch (e: Exception) {
                    Log.e("ChatViewModel", "Failed to parse content in convertDtoToEntity", e)
                    null
                }

                val displayText = assistantContent?.assistantReply
                    ?: assistantContent?.summary
                    ?: dto.content

                MessageEntity.AssistantResponse(
                    id = dto.id,
                    text = displayText,
                    action = parseActionFromAssistantContent(assistantContent)
                )
            }
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

                                    val noteMessage = AssistantResponse(
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
                                    val todoMessage = AssistantResponse(
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

                        ActionType.JOURNAL -> {

                        }
                        ActionType.WEEKLY_PLAN -> {


                        }
                        ActionType.PROJECT_IDEA -> {

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
                Log.i("ChatViewModel", "conversationId: $conversationId")
                loadConversationMessages(conversationId)
            } catch (e: Exception) {
                Log.e("ChatViewModel", "conversationId error: ${e.message}")
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
            Log.e("ChatViewModel", "Load conversation error: ${e.message}")
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