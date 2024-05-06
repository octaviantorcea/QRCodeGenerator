package com.example.qrcodegenerator.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import coil.network.HttpException
import com.example.qrcodegenerator.QRCodeGeneratorApplication
import com.example.qrcodegenerator.data.AuthRepository
import com.example.qrcodegenerator.data.QRCodeGeneratorUiState
import com.example.qrcodegenerator.model.AuthServiceBasicResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import okio.IOException

class QRCodeGeneratorViewModel(
    private val authRepository: AuthRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(QRCodeGeneratorUiState())
    val uiState: StateFlow<QRCodeGeneratorUiState> = _uiState.asStateFlow()

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

    fun register(username: String, password: String) {
        viewModelScope.launch {
            try {
                println(authRepository.register(username, password).message)
            } catch (e: Exception) {
                println("error: $e")
            }
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
