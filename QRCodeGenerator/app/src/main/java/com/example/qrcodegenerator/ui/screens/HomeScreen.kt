package com.example.qrcodegenerator.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.qrcodegenerator.R

@Composable
fun HomeScreen(
    isLogged: Boolean,
    modifier: Modifier = Modifier,
    onClickRegister: () -> Unit,
    onClickLogin: () -> Unit,
    onClickLogout: () -> Unit,
    onClickGenerate: () -> Unit,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
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
        CustomIconButton(
            buttonText = stringResource(id = R.string.generate_qr_code),
            iconPainterResource = painterResource(id = R.drawable.qr_code),
            onClick = onClickGenerate,
            modifier = modifier
                .height(70.dp)
        )

        if (isLogged) {
            CustomIconButton(
                buttonText = "View Saved Codes",
                iconPainterResource = painterResource(id = R.drawable.save),
                onClick = {},
                modifier = modifier
                    .padding(top = 16.dp)
                    .height(70.dp)
            )
        }

        Spacer(
            modifier = modifier
                .weight(1f)
        )
        LoginButtons(
            isLogged = isLogged,
            onClickLogin = onClickLogin,
            onClickRegister = onClickRegister,
            onClickLogout = onClickLogout
        )
    }
}

@Composable
fun LoginButtons(
    isLogged: Boolean,
    onClickLogin: () -> Unit,
    onClickRegister: () -> Unit,
    onClickLogout: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .padding(start = 12.dp, end = 12.dp)
    ) {
        if (!isLogged) {
           CustomIconButton(
               buttonText = stringResource(id = R.string.login),
               iconPainterResource = painterResource(id = R.drawable.login_7),
               onClick = onClickLogin,
               modifier = Modifier.width(180.dp)
           )
            Spacer(modifier = Modifier
                .weight(1f))
            CustomIconButton(
                buttonText = stringResource(id = R.string.register),
                iconPainterResource = painterResource(id = R.drawable.new_registration_icon),
                onClick = onClickRegister,
                modifier = Modifier.width(180.dp)
            )
        } else {
            CustomIconButton(
                buttonText = stringResource(id = R.string.logout),
                iconPainterResource = painterResource(id = R.drawable.logout),
                onClick = onClickLogout,
                modifier = Modifier.width(180.dp)
            )
        }
    }
}

@Preview(showSystemUi = true, showBackground = true, device = Devices.PHONE)
@Composable
fun HomeScreenPreview() {
    HomeScreen(
        isLogged = false,
        onClickRegister = {},
        onClickLogin = {},
        onClickLogout = {},
        onClickGenerate = {}
    )
}
