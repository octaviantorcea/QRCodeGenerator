package com.example.qrcodegenerator.ui.screens

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.qrcodegenerator.R

@Composable
fun QRCodeMainScreen(
    isLoggedIn: Boolean,
    imageBitmap: ImageBitmap,
    onSaveQRCode: () -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .padding(
                top = contentPadding
                    .calculateTopPadding()
                    .plus(16.dp),
                bottom = contentPadding
                    .calculateBottomPadding()
                    .plus(16.dp)
            )
            .fillMaxSize()
    ) {
        Image(
            bitmap = imageBitmap,
            contentDescription = stringResource(R.string.qr_code),
            modifier = Modifier
                .padding(bottom = 16.dp)
        )

        if (isLoggedIn) {
            CustomIconButton(
                buttonText = "Save code",
                iconPainterResource = painterResource(id = R.drawable.save),
                onClick = onSaveQRCode
            )
        }
    }
}

@Preview(showSystemUi = true, device = Devices.PHONE)
@Composable
fun PreviewMainScreen() {
    QRCodeMainScreen(
        isLoggedIn = true,
        imageBitmap = Bitmap.createBitmap(800, 800, Bitmap.Config.ARGB_8888).asImageBitmap(),
        onSaveQRCode = {}
    )
}
