package com.example.qrcodegenerator.ui.screens

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.qrcodegenerator.R
import com.example.qrcodegenerator.data.LoginStatus

@Composable
fun LoginScreen(
    inputUsername: String,
    inputPassword: String,
    onUsernameChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onClickLogin: () -> Unit,
    onWrongLogin: () -> Unit,
    onCompleteLogin: () -> Unit,
    loginStatus: LoginStatus,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    LoginRegistrationUI(
        inputUsername = inputUsername,
        inputPassword = inputPassword,
        onUsernameChange = onUsernameChange,
        onPasswordChange = onPasswordChange,
        mainButtonText = stringResource(id = R.string.login),
        mainButtonPainter = painterResource(id = R.drawable.login_7),
        onClickMainButton = onClickLogin,
        modifier = modifier,
        contentPadding = contentPadding
    )

    when (loginStatus) {
        LoginStatus.NOT_STARTED -> {}

        LoginStatus.NO_USERNAME_OR_PASSWORD -> CustomAlertDialog(
            title = stringResource(id = R.string.empty_user_or_password_title),
            text = stringResource(id = R.string.empty_user_or_password_text_login),
            confirmButtonText = stringResource(id = R.string.ok),
            onConfirmButton = onWrongLogin
        )

        LoginStatus.USER_NOT_REGISTERED -> CustomAlertDialog(
            title = stringResource(id = R.string.wrong_user_title),
            text = stringResource(id = R.string.wrong_user_text),
            confirmButtonText = stringResource(id = R.string.ok),
            onConfirmButton = onWrongLogin
        )

        LoginStatus.WRONG_PASSWORD -> CustomAlertDialog(
            title = stringResource(id = R.string.wrong_password_title),
            text = stringResource(id = R.string.wrong_password_text),
            confirmButtonText = stringResource(id = R.string.ok),
            onConfirmButton = onWrongLogin
        )

        LoginStatus.IN_PROGRESS -> CustomAlertDialog(
            title = stringResource(id = R.string.in_progress_title),
            text = stringResource(id = R.string.in_progress_text_login),
            confirmButtonText = null,
            onConfirmButton = {}
        )

        LoginStatus.UNKNOWN_ERROR -> CustomAlertDialog(
            title = stringResource(id = R.string.unknown_error_title),
            text = stringResource(id = R.string.unknown_error_text),
            confirmButtonText = stringResource(id = R.string.ok),
            onConfirmButton = onWrongLogin
        )

        LoginStatus.COMPLETED -> CustomAlertDialog(
            title = stringResource(id = R.string.login_complete_title),
            text = stringResource(id = R.string.login_complete_text),
            confirmButtonText = stringResource(id = R.string.ok),
            onConfirmButton = onCompleteLogin
        )
    }
}
