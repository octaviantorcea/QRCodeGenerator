package com.example.qrcodegenerator.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
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
    var passwordVisible by rememberSaveable { mutableStateOf(false) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
            .padding(
                top = contentPadding
                    .calculateTopPadding()
                    .plus(16.dp),
                bottom = contentPadding
                    .calculateBottomPadding()
                    .plus(40.dp)
            )
            .fillMaxSize()
    ) {
        TextField(
            value = inputUsername,
            onValueChange = onUsernameChange,
            label = { Text(text = "New Username") },
            singleLine = true,
            modifier = modifier.padding(bottom = 20.dp)
        )
        TextField(
            value = inputPassword,
            onValueChange = onPasswordChange,
            label = { Text(text = "New Password") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            singleLine = true,
            visualTransformation = if (passwordVisible) {
                VisualTransformation.None
            } else {
                PasswordVisualTransformation()
            },
            trailingIcon = {
                val image = if (passwordVisible) {
                    painterResource(id = R.drawable.visibility)
                } else {
                    painterResource(id = R.drawable.visibility_off)
                }

                val description = if (passwordVisible) "Hide password" else "Show password"

                IconButton(
                    onClick = { passwordVisible = !passwordVisible }
                ) {
                    Icon(
                        painter = image,
                        contentDescription = description,
                        Modifier.size(32.dp)
                    )
                }
            },
            modifier = modifier.padding(bottom = 20.dp)
        )
        CustomIconButton(
            buttonText = "Register",
            iconPainterResource = painterResource(id = R.drawable.new_registration_icon),
            onClick = { onClickRegister() }
        )
    }

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
