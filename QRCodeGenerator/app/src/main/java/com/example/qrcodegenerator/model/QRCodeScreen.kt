package com.example.qrcodegenerator.model

import androidx.annotation.StringRes
import com.example.qrcodegenerator.R

enum class QRCodeScreen(@StringRes val title: Int) {
    HomeScreen(title = R.string.app_name),
    RegistrationScreen(title = R.string.register),
    LoginScreen(title = R.string.login)
}
