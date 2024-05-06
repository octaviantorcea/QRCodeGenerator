package com.example.qrcodegenerator.data

enum class RegistrationStatus {
    NOT_STARTED,
    NO_USERNAME_OR_PASSWORD,
    DUPLICATE_USERNAME,
    IN_PROGRESS,
    UNKNOWN_ERROR,
    COMPLETED
}

data class QRCodeGeneratorUiState(
    val isLogged: Boolean = false,
    val registrationStatus: RegistrationStatus = RegistrationStatus.NOT_STARTED,
)
