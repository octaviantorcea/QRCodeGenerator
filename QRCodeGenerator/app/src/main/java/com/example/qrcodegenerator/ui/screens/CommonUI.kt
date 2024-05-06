package com.example.qrcodegenerator.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.qrcodegenerator.R

@Composable
fun CustomIconButton(
    buttonText: String,
    iconPainterResource: Painter,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        modifier = modifier,
        onClick = onClick
    ) {
        Icon(
            painter = iconPainterResource,
            contentDescription = stringResource(R.string.qr_code_icon),
            modifier = Modifier
                .padding(end = 8.dp)
                .size(32.dp)
        )
        Text(
            text = buttonText,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
        )
    }
}

@Composable
fun CustomAlertDialog(
    title: String,
    text: String,
    confirmButtonText: String?,
    onConfirmButton: () -> Unit
) {
    AlertDialog(
        title = { Text(text = title) },
        text = { Text(text = text) },
        onDismissRequest = onConfirmButton,
        confirmButton = {
            if (confirmButtonText != null) {
                TextButton(
                    onClick = onConfirmButton
                ) {
                    Text(text = confirmButtonText)
                }
            }
        }
    )
}

@Composable
fun LoginRegistrationUI(
    inputUsername: String,
    inputPassword: String,
    onUsernameChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    mainButtonText: String,
    mainButtonPainter: Painter,
    onClickMainButton: () -> Unit,
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
            label = { Text(text = stringResource(R.string.username)) },
            singleLine = true,
            modifier = modifier.padding(bottom = 20.dp)
        )
        TextField(
            value = inputPassword,
            onValueChange = onPasswordChange,
            label = { Text(text = stringResource(R.string.password)) },
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
            buttonText = mainButtonText,
            iconPainterResource = mainButtonPainter,
            onClick = { onClickMainButton() }
        )
    }
}
