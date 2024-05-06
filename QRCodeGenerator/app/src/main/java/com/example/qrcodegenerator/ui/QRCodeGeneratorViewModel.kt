package com.example.qrcodegenerator.ui

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.qrcodegenerator.QRCodeGeneratorApplication
import com.example.qrcodegenerator.data.AuthRepository
import com.example.qrcodegenerator.data.QRCodeGeneratorUiState
import com.example.qrcodegenerator.data.RegistrationStatus
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class QRCodeGeneratorViewModel(
    private val authRepository: AuthRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(QRCodeGeneratorUiState())
    val uiState: StateFlow<QRCodeGeneratorUiState> = _uiState.asStateFlow()

    var username by mutableStateOf("")
        private set

    var password by mutableStateOf("")
        private set

    fun updateUsername(updatedUsername: String) {
        username = updatedUsername
    }

    fun updatePassword(updatedPassword: String) {
        password = updatedPassword
    }

    fun login() {
        _uiState.update {
            it.copy(
                isLogged = true
            )
        }
    }

    fun logout() {
        _uiState.update {
            it.copy(
                isLogged = false
            )
        }
    }

    fun resetRegistrationStatus() {
        updateRegistrationStatus(RegistrationStatus.NOT_STARTED)
    }

    fun resetUsernameAndPassword() {
        username = ""
        password = ""
    }

    fun register() {
        if (username.isEmpty() or password.isEmpty()) {
            updateRegistrationStatus(RegistrationStatus.NO_USERNAME_OR_PASSWORD)

            return
        }

        updateRegistrationStatus(RegistrationStatus.IN_PROGRESS)

        viewModelScope.launch {
            try {
                val response = authRepository.register(username, password)

                if (response.isSuccessful) {
                    Log.i("viewModel", response.body()!!.message)

                    updateRegistrationStatus(RegistrationStatus.COMPLETED)
                } else {
                    if (response.code() == 400) {
                        updateRegistrationStatus(RegistrationStatus.DUPLICATE_USERNAME)
                    } else {
                        Log.e("viewModel", "unknown error: ${response.code()} ${response.message()}")

                        updateRegistrationStatus(RegistrationStatus.UNKNOWN_ERROR)
                    }
                }
            } catch (e: Exception) {
                Log.e("viewModel", "unknown error: $e")

                updateRegistrationStatus(RegistrationStatus.UNKNOWN_ERROR)
            }
        }
    }

    private fun updateRegistrationStatus(status: RegistrationStatus) {
        _uiState.update {
            it.copy(
                registrationStatus = status
            )
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as QRCodeGeneratorApplication)
                val authRepository = application.container.authRepository
                QRCodeGeneratorViewModel(authRepository = authRepository)
            }
        }
    }
}
