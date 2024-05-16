package com.example.qrcodegenerator.data

import android.graphics.Bitmap
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap

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

data class QRCodeGeneratorUiState(
    val isLogged: Boolean = false,
    val registrationStatus: RegistrationStatus = RegistrationStatus.NOT_STARTED,
    val loginStatus: LoginStatus = LoginStatus.NOT_STARTED,
    val token: String = "",
    val imageBitmap: ImageBitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888).asImageBitmap(),
    val tempBytesArray: ByteArray = ByteArray(1)
)
