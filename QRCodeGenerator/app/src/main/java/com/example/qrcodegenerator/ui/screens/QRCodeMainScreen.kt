package com.example.qrcodegenerator.ui.screens

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import com.example.qrcodegenerator.data.GenerateCodeStatus
import com.example.qrcodegenerator.data.SaveCodeStatus

@Composable
fun QRCodeMainScreen(
    isLoggedIn: Boolean,
    fetchImageBitmapMethod: () -> ImageBitmap,
    onSaveQRCode: () -> Unit,
    generateCodeStatus: GenerateCodeStatus,
    modifier: Modifier = Modifier,
    saveCodeStatus: SaveCodeStatus,
    onDismiss: () -> Unit,
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
        when (generateCodeStatus) {
            GenerateCodeStatus.NOT_STARTED -> { }

            GenerateCodeStatus.IN_PROGRESS -> {
                Image(
                    painter = painterResource(id = R.drawable.loading_img),
                    contentDescription = stringResource(R.string.generating_qr_code),
                    modifier = Modifier.size(500.dp)
                )
            }

            GenerateCodeStatus.COMPLETED -> {
                Image(
                    bitmap = fetchImageBitmapMethod(),
                    contentDescription = stringResource(R.string.qr_code),
                    modifier = Modifier
                        .padding(bottom = 16.dp)
                )
            }

            GenerateCodeStatus.ERROR -> {
                Image(
                    painter = painterResource(id = R.drawable.ic_broken_image),
                    contentDescription = stringResource(R.string.broken_qr_code),
                    modifier = Modifier.size(500.dp)
                )
            }
        }

        if (isLoggedIn && generateCodeStatus == GenerateCodeStatus.COMPLETED) {
            CustomIconButton(
                buttonText = "Save code",
                iconPainterResource = painterResource(id = R.drawable.save),
                onClick = onSaveQRCode
            )
        }

        var title = ""
        var text = ""

        when (saveCodeStatus) {
            SaveCodeStatus.NOT_STARTED -> { }

            SaveCodeStatus.IN_PROGRESS -> {
                title = stringResource(id = R.string.in_progress_title)
                text = stringResource(id = R.string.in_progress_save_code_text)
            }

            SaveCodeStatus.COMPLETED -> {
                title = stringResource(id = R.string.complete_title)
                text = stringResource(id = R.string.complete_save_code_text)
            }

            SaveCodeStatus.UNAUTHORIZED -> {
                title = stringResource(id = R.string.unauthorized_title)
                text = stringResource(id = R.string.unauthorized_text)
            }

            SaveCodeStatus.SERVER_ERROR -> {
                title = stringResource(id = R.string.unknown_error_title)
                text = stringResource(id = R.string.unknown_error_text)
            }

            SaveCodeStatus.DUPLICATED -> {
                title = stringResource(id = R.string.duplicated_code_title)
                text = stringResource(id = R.string.duplicated_code_text)
            }

            SaveCodeStatus.BAD_REQUEST -> {
                title = stringResource(id = R.string.bad_request_title)
                text = stringResource(id = R.string.bad_request_text)
            }
        }

        if (saveCodeStatus != SaveCodeStatus.NOT_STARTED) {
            CustomAlertDialog(
                title = title,
                text = text,
                confirmButtonText = stringResource(id = R.string.ok),
                onConfirmButton = onDismiss
            )
        }
    }
}

@Preview(showSystemUi = true, device = Devices.PHONE)
@Composable
fun PreviewMainScreen() {
    QRCodeMainScreen(
        isLoggedIn = true,
        fetchImageBitmapMethod = {
            Bitmap.createBitmap(800, 800, Bitmap.Config.ARGB_8888).asImageBitmap()
        },
        onSaveQRCode = {},
        generateCodeStatus = GenerateCodeStatus.IN_PROGRESS,
        saveCodeStatus = SaveCodeStatus.NOT_STARTED,
        onDismiss = {}
    )
}
