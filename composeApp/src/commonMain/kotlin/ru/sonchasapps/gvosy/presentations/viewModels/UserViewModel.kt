package ru.sonchasapps.gvosy.presentations.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.sonchasapps.gvosy.data.models.AuthRequest
import ru.sonchasapps.gvosy.data.models.LogInRequest
import ru.sonchasapps.gvosy.data.models.UserEntity
import ru.sonchasapps.gvosy.data.repositories.UserRepository
import ru.sonchasapps.gvosy.domain.AuthUiState

class UserViewModel(
        private val repo: UserRepository
    ) : ViewModel() {

    private val _user = MutableStateFlow<UserEntity?>(null)
    val user: StateFlow<UserEntity?> = _user

    private val _state = MutableStateFlow<AuthUiState>(AuthUiState.Idle)
    val state: StateFlow<AuthUiState> = _state

    fun registerUser(name: String, email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _state.value = AuthUiState.Loading
            println("AuthVM: start registerUser(email=$email)")
            val request = AuthRequest(name, email, password)
            val result = repo.registerUser(request)
            println("AuthVM: result = $result")
            _state.value = if (result.isSuccess) {
                val user = result.getOrNull()
                _user.value = user
                AuthUiState.Success(user)
            } else {
                AuthUiState.Error(result.exceptionOrNull()?.message)
            }
        }
    }

    fun loginUser(email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _state.value = AuthUiState.Loading
            val request = LogInRequest(email, password)
            val result = repo.loginUser(request)
            _state.value = if (result.isSuccess) {
                val user = result.getOrNull()
                _user.value = user
                AuthUiState.Success(user)
            } else {
                AuthUiState.Error(result.exceptionOrNull()?.message)
            }
        }
    }
    fun logout(user : UserEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.logout(user)
            _user.value = null
        }
    }
    fun getUser() : UserEntity? {
        viewModelScope.launch(Dispatchers.IO) {
            _user.value =  repo.getCurrentUser()
        }
        return _user.value
    }



}