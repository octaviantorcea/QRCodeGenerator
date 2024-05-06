package com.example.qrcodegenerator.ui.screens

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.qrcodegenerator.R
import com.example.qrcodegenerator.data.RegistrationStatus

@Composable
fun RegistrationScreen(
    inputUsername: String,
    inputPassword: String,
    onUsernameChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onClickRegister: () -> Unit,
    onWrongRegistration: () -> Unit,
    onCompleteRegistration: () -> Unit,
    registrationStatus: RegistrationStatus,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    LoginRegistrationUI(
        inputUsername = inputUsername,
        inputPassword = inputPassword,
        onUsernameChange = onUsernameChange,
        onPasswordChange = onPasswordChange,
        mainButtonText = stringResource(id = R.string.register),
        mainButtonPainter = painterResource(id = R.drawable.new_registration_icon),
        onClickMainButton = onClickRegister,
        modifier = modifier,
        contentPadding = contentPadding
    )

    when (registrationStatus) {
        RegistrationStatus.NOT_STARTED -> {}

        RegistrationStatus.NO_USERNAME_OR_PASSWORD -> CustomAlertDialog(
            title = stringResource(id = R.string.empty_user_or_password_title),
            text = stringResource(id = R.string.empty_user_or_password_text),
            confirmButtonText = stringResource(id = R.string.ok),
            onConfirmButton = onWrongRegistration
        )

        RegistrationStatus.COMPLETED -> CustomAlertDialog(
            title = stringResource(id = R.string.registration_complete_title),
            text = stringResource(id = R.string.registration_complete_text),
            confirmButtonText = stringResource(id = R.string.ok),
            onConfirmButton = onCompleteRegistration
        )

        RegistrationStatus.DUPLICATE_USERNAME -> CustomAlertDialog(
            title = stringResource(id = R.string.duplicate_username_title),
            text = stringResource(id = R.string.duplicate_username_text),
            confirmButtonText = stringResource(id = R.string.ok),
            onConfirmButton = onWrongRegistration
        )

        RegistrationStatus.IN_PROGRESS -> CustomAlertDialog(
            title = stringResource(id = R.string.in_progress_title),
            text = stringResource(id = R.string.in_progress_text),
            confirmButtonText = null,
            onConfirmButton = {}
        )

        RegistrationStatus.UNKNOWN_ERROR -> CustomAlertDialog(
            title = stringResource(id = R.string.unknown_error_title),
            text = stringResource(id = R.string.unknown_error_text),
            confirmButtonText = stringResource(id = R.string.ok),
            onConfirmButton = onWrongRegistration
        )
    }
}
