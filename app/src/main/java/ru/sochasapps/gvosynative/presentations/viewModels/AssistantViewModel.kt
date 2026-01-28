package ru.sochasapps.gvosynative.presentations.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.sochasapps.gvosynative.data.models.AssistantEntity
import ru.sochasapps.gvosynative.data.dto.AssistantRequest
import ru.sochasapps.gvosynative.data.repositories.AssistantRepository
import ru.sochasapps.gvosynative.data.repositories.UserRepository

import ru.sochasapps.gvosynative.domain.state.AssistantState

class AssistantViewModel(
    private val repo: AssistantRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    private val _assistant = MutableStateFlow<AssistantEntity?>(null)
    val assistant: StateFlow<AssistantEntity?> = _assistant

    private val _state = MutableStateFlow<AssistantState>(AssistantState.Idle)
    val state: StateFlow<AssistantState> = _state

    fun addAssistant(name: String, age: Int, sex: Boolean, desc: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val token = userRepository.getCurrentUser()?.userToken
            if (!token.isNullOrEmpty()) {
                val request = AssistantRequest(
                    name, age,
                    sex, desc,
                    token
                )
                val result = repo.addAssistant(request)
                _state.value = if(result.isSuccess) {
                    val assistant = result.getOrNull()
                    _assistant.value = assistant
                    AssistantState.Success(assistant)
                } else {
                    AssistantState.Error(result.exceptionOrNull()?.message)
                }
            }
        }
    }

//    fun deleteAssistant (assistant : AssistantEntity){
//        viewModelScope.launch(Dispatchers.IO) {
//            repo.deleteAssistant(assistant)
//            _assistant.value = assistant
//        }
//    }

    fun getAssistant(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            val u = repo.getAssistant(id)
            _assistant.value = u
        }
    }
}