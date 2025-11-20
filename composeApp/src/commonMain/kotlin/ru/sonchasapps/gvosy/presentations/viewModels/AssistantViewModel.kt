package ru.sonchasapps.gvosy.presentations.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.sonchasapps.gvosy.data.models.AssistantEntity
import ru.sonchasapps.gvosy.data.repositories.AssistantRepository

class AssistantViewModel(private val repo : AssistantRepository) : ViewModel() {

    private val _assistant = MutableStateFlow<AssistantEntity?>(null)
    val assistant: StateFlow<AssistantEntity?> = _assistant

    fun insertAssistant (assistant : AssistantEntity){
        viewModelScope.launch {
            repo.insertAssistant(assistant)
            _assistant.value = assistant
        }
    }

    fun updateAssistant (assistant : AssistantEntity){
        viewModelScope.launch {
            repo.updateAssistant(assistant)
            _assistant.value = assistant
        }
    }

    fun deleteAssistant (assistant : AssistantEntity){
        viewModelScope.launch {
            repo.deleteAssistant(assistant)
            _assistant.value = assistant
        }
    }

    fun loadUser(id: Long) {
        viewModelScope.launch {
            val u = repo.getAssistant(id)
            _assistant.value = u
        }
    }



}