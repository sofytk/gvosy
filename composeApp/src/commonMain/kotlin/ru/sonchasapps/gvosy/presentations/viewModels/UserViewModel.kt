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
import ru.sonchasapps.gvosy.domain.AuthState
import ru.sonchasapps.gvosy.domain.AuthUiState

class UserViewModel(
        private val repo: UserRepository
    ) : ViewModel() {

    private val _user = MutableStateFlow<UserEntity?>(null)
    val user: StateFlow<UserEntity?> = _user

    private val _state = MutableStateFlow<AuthUiState>(AuthUiState.Idle)
    val state: StateFlow<AuthUiState> = _state

    private val _authState = MutableStateFlow<AuthState>(AuthState.NotAuthorized)
    val authState : StateFlow<AuthState> = _authState


    fun checkAuth(){
        viewModelScope.launch(Dispatchers.IO){
            _authState.value = AuthState.Loading
            _user.value = repo.getCurrentUser()
            val token = _user.value?.userToken
            if(token.isNullOrEmpty()) _authState.value = AuthState.NotAuthorized
            else _authState.value = AuthState.Authorized
            println("UserToken: $token")
        }
    }


fun deleteAll(){
    viewModelScope.launch(Dispatchers.IO){
        repo.deleteAll()
    }
}
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
                _authState.value = AuthState.Authorized
                AuthUiState.Success(user)
            } else {
                _authState.value = AuthState.NotAuthorized
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
                _authState.value = AuthState.Authorized
                AuthUiState.Success(user)
            } else {
                _authState.value = AuthState.NotAuthorized
                AuthUiState.Error(result.exceptionOrNull()?.message)
            }
        }
    }
    fun logout() {
        viewModelScope.launch(Dispatchers.IO) {
            repo.logout()
            _user.value = null
            _authState.value = AuthState.NotAuthorized
        }
    }
    fun getUser() : UserEntity? {
        viewModelScope.launch(Dispatchers.IO) {
            _user.value = repo.getCurrentUser()
            println("getUser: ${_user.value?.userName}")
        }
        return _user.value
    }
}