package com.example.qrcodegenerator.ui.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
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
