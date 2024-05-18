package com.example.qrcodegenerator.model

import androidx.annotation.StringRes
import com.example.qrcodegenerator.R

enum class QRCodeScreen(@StringRes val title: Int) {
    HomeScreen(title = R.string.app_name),
    RegistrationScreen(title = R.string.register),
    LoginScreen(title = R.string.login),
    QRCodeParamsScreen(title = R.string.qr_code_params_screen_title),
    QRCodeMainScreen(title = R.string.qr_code_main_screen_title),
    SavedCodesScreen(title = R.string.saved_qr_codes)
}
