package com.example.qrcodegenerator.data

import com.example.qrcodegenerator.ui.SavedCodesDataForScreen

enum class RegistrationStatus {
    NOT_STARTED,
    NO_USERNAME_OR_PASSWORD,
    DUPLICATE_USERNAME,
    IN_PROGRESS,
    UNKNOWN_ERROR,
    COMPLETED
}

enum class LoginStatus {
    NOT_STARTED,
    NO_USERNAME_OR_PASSWORD,
    USER_NOT_REGISTERED,
    WRONG_PASSWORD,
    IN_PROGRESS,
    UNKNOWN_ERROR,
    COMPLETED
}

enum class GenerateCodeStatus {
    NOT_STARTED,
    IN_PROGRESS,
    COMPLETED,
    ERROR
}

enum class SaveCodeStatus {
    NOT_STARTED,
    IN_PROGRESS,
    COMPLETED,
    UNAUTHORIZED,
    SERVER_ERROR,
    DUPLICATED,
    BAD_REQUEST
}

enum class GetSavedCodesStatus {
    NOT_STARTED,
    IN_PROGRESS,
    COMPLETED
}


data class QRCodeGeneratorUiState(
    val isLogged: Boolean = false,
    val registrationStatus: RegistrationStatus = RegistrationStatus.NOT_STARTED,
    val loginStatus: LoginStatus = LoginStatus.NOT_STARTED,
    val generateCodeStatus: GenerateCodeStatus = GenerateCodeStatus.NOT_STARTED,
    val saveCodeStatus: SaveCodeStatus = SaveCodeStatus.NOT_STARTED,
    val getSavedCodesStatus: GetSavedCodesStatus = GetSavedCodesStatus.NOT_STARTED,
    val token: String = "",
    val imageByteArray: ByteArray = ByteArray(1),
    val savedCodes: MutableList<SavedCodesDataForScreen> = mutableListOf()
)
