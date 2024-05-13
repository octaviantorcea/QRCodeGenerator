package com.example.qrcodegenerator.ui

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.qrcodegenerator.QRCodeGeneratorApplication
import com.example.qrcodegenerator.data.AuthRepository
import com.example.qrcodegenerator.data.LoginStatus
import com.example.qrcodegenerator.data.QRCodeGeneratorUiState
import com.example.qrcodegenerator.data.RegistrationStatus
import com.example.qrcodegenerator.model.AuthServiceBasicResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream

class QRCodeGeneratorViewModel(
    private val authRepository: AuthRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(QRCodeGeneratorUiState())
    val uiState: StateFlow<QRCodeGeneratorUiState> = _uiState.asStateFlow()

    var username by mutableStateOf("")
        private set

    var password by mutableStateOf("")
        private set

    var encodedData by mutableStateOf("")
        private set

    var codeRed by mutableStateOf("0")
        private set

    var codeGreen by mutableStateOf("0")
        private set

    var codeBlue by mutableStateOf("0")
        private set

    fun updateUsername(updatedUsername: String) {
        username = updatedUsername
    }

    fun updatePassword(updatedPassword: String) {
        password = updatedPassword
    }

    fun updateEncodedData(updatedEncodedData: String) {
        encodedData = updatedEncodedData
    }

    fun updateRed(updatedRed: String) {
        codeRed = updatedRed
    }

    fun updateGreen(updatedGreen: String) {
        codeGreen = updatedGreen
    }

    fun updateBlue(updatedBlue: String) {
        codeBlue = updatedBlue
    }

    private fun validateStringIntForColor(stringInt: String): Int {
        var result: Int

        try {
            result = stringInt.toInt()

            if (result > 255 || result < 0) {
                throw Exception()
            }
        } catch (e: Exception) {
            result = 0
        }

        return result
    }

    fun getQRCodeColor(): Color {
        return Color(
            validateStringIntForColor(codeRed),
            validateStringIntForColor(codeGreen),
            validateStringIntForColor(codeBlue),
        )
    }

    @OptIn(ExperimentalSerializationApi::class)
    fun login() {
        if (username.isEmpty() or password.isEmpty()) {
            updateLoginStatus(LoginStatus.NO_USERNAME_OR_PASSWORD)

            return
        }

        updateLoginStatus(LoginStatus.IN_PROGRESS)

        viewModelScope.launch {
            try {
                val response = authRepository.login(username, password)

                if (response.isSuccessful) {
                    Log.i("viewModel", response.body()!!.token)

                    _uiState.update {
                        it.copy(
                            isLogged = true,
                            token = response.body()!!.token
                        )
                    }

                    updateLoginStatus(LoginStatus.COMPLETED)
                } else {
                    if (response.code() == 401) {
                        val error: AuthServiceBasicResponse = Json.decodeFromStream<AuthServiceBasicResponse>(response.errorBody()!!.byteStream())

                        when (error.message) {
                            "Username not registered" -> updateLoginStatus(LoginStatus.USER_NOT_REGISTERED)
                            "Invalid password" -> updateLoginStatus(LoginStatus.WRONG_PASSWORD)
                            else -> {
                                Log.e("viewModel", "unknown error: ${error.message}")

                                updateLoginStatus(LoginStatus.UNKNOWN_ERROR)
                            }
                        }
                    } else {
                        Log.e("viewModel", "unknown error: ${response.code()} ${response.message()}")

                        updateLoginStatus(LoginStatus.UNKNOWN_ERROR)
                    }
                }
            } catch (e: Exception) {
                Log.e("viewModel", "unknown error: $e")

                updateLoginStatus(LoginStatus.UNKNOWN_ERROR)
            }
        }
    }

    fun logout() {
        _uiState.update {
            it.copy(
                isLogged = false,
                loginStatus = LoginStatus.NOT_STARTED,
                token = ""
            )
        }
    }

    fun resetRegistrationStatus() {
        updateRegistrationStatus(RegistrationStatus.NOT_STARTED)
    }

    fun resetLoginStatus() {
        updateLoginStatus(LoginStatus.NOT_STARTED)
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

    private fun updateLoginStatus(status: LoginStatus) {
        _uiState.update {
            it.copy(
                loginStatus = status
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
