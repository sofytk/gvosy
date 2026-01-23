package ru.sochasapps.gvosynative.domain.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.sochasapps.gvosynative.AppPreferences

class FirstLaunchViewModel(
    private val appPreferences: AppPreferences
) : ViewModel() {

    private val _uiState = MutableStateFlow<FirstLaunchUIState>(FirstLaunchUIState.Loading)
    val uiState: StateFlow<FirstLaunchUIState> = _uiState.asStateFlow()

    init {
        checkFirstLaunch()
    }

    private fun checkFirstLaunch() {
        viewModelScope.launch {
            val isFirstLaunch = appPreferences.getIsFirstLaunch()

            if (isFirstLaunch) {
                _uiState.value = FirstLaunchUIState.FirstLaunch
            } else {
                val wasAsked = appPreferences.wasPermissionAsked()
                val isGranted = appPreferences.isPermissionGranted()

                _uiState.value = when {
                    isGranted -> FirstLaunchUIState.PermissionGranted
                    wasAsked -> FirstLaunchUIState.PermissionPreviouslyDenied
                    else -> FirstLaunchUIState.NeedPermission
                }
            }
        }
    }

    fun onFirstLaunchCompleted() {
        viewModelScope.launch {
            appPreferences.setFirstLaunchCompleted()
        }
    }

    fun onPermissionAsked() {
        viewModelScope.launch {
            appPreferences.setPermissionAsked()
        }
    }

    fun onPermissionGranted(granted: Boolean) {
        viewModelScope.launch {
            appPreferences.setPermissionGranted(granted)
        }
    }
}

sealed class FirstLaunchUIState {
    object Loading : FirstLaunchUIState()
    object FirstLaunch : FirstLaunchUIState()
    object NeedPermission : FirstLaunchUIState()
    object PermissionGranted : FirstLaunchUIState()
    object PermissionPreviouslyDenied : FirstLaunchUIState()
}