package ru.sonchasapps.gvosy.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.sonchasapps.gvosy.database.entities.UserEntity
import ru.sonchasapps.gvosy.database.repositories.UserRepository

class UserViewModel(
        private val repo: UserRepository
    ) : ViewModel() {

        private val _user = MutableStateFlow<UserEntity?>(null)
        val user: StateFlow<UserEntity?> = _user

        fun loadUser(id: Long) {
            viewModelScope.launch {
                val u = repo.getUserData(id)
                _user.value = u
            }
        }

        fun addUser(user: UserEntity) {
            viewModelScope.launch {
                repo.insertUser(user)
                _user.value = user
            }
        }

        fun updateUser(user: UserEntity) {
            viewModelScope.launch {
                repo.updateUserData(user)
                _user.value = user
            }
        }

        fun deleteUser(user: UserEntity) {
            viewModelScope.launch {
                repo.deleteUser(user)
                _user.value = null
            }
        }

    }