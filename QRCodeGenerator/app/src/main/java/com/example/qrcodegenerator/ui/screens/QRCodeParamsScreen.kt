package com.example.qrcodegenerator.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.qrcodegenerator.R

@Composable
fun QRCodeParamsScreen(
    onGenerateQRCode: () -> Unit,
    encodedData: String,
    onUpdateEncodedData: (String) -> Unit,
    codeRed: String,
    codeGreen: String,
    codeBlue: String,
    onUpdateRed: (String) -> Unit,
    onUpdateGreen: (String) -> Unit,
    onUpdateBlue: (String) -> Unit,
    textColor: Color,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    var isDropDownExpanded by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .padding(
                top = contentPadding
                    .calculateTopPadding()
                    .plus(16.dp),
                bottom = contentPadding
                    .calculateBottomPadding()
                    .plus(40.dp)
            )
            .fillMaxHeight()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .weight(2.5f)
                .fillMaxSize()
        ) {
            OutlinedTextField(
                value = encodedData,
                onValueChange = onUpdateEncodedData,
                label = { Text(text = stringResource(R.string.data)) },
                singleLine = true,
                shape = RoundedCornerShape(24.dp),
                modifier = modifier
                    .padding(bottom = 16.dp)
            )
            Button(
                onClick = {
                    if (encodedData.isNotEmpty()) {
                        onGenerateQRCode()
                    }
                },
                shape = RoundedCornerShape(24.dp),
                modifier = Modifier.size(200.dp)
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.qr_code),
                        contentDescription = stringResource(id = R.string.generate_qr_code)
                    )
                    Text(
                        text = stringResource(id = R.string.generate_qr_code),
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.bodyMedium,
                        fontSize = 18.sp
                    )
                }
            }
        }

        Column(
            modifier = Modifier
                .align(Alignment.Start)
                .weight(1f)
                .fillMaxSize()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                IconButton(
                    onClick = { isDropDownExpanded = !isDropDownExpanded }
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = stringResource(R.string.drop_down_arrow),
                        modifier = modifier
                            .rotate(if (isDropDownExpanded) 270f else 0f)
                            .size(40.dp)
                    )
                }
                Text(
                    text = stringResource(R.string.additional_options),
                    style = MaterialTheme.typography.bodyMedium,
                    fontStyle = FontStyle.Italic,
                    fontSize = 20.sp
                )
            }
            if (isDropDownExpanded) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .padding(bottom = 20.dp)
                ) {
                    Text(
                        color = textColor,
                        style = MaterialTheme.typography.bodySmall,
                        text = "Color: ",
                        fontSize = 32.sp,
                        modifier = Modifier
                            .padding(start = 16.dp)
                            .weight(1.3f)
                    )
                    OutlinedTextField(
                        label = { Text(stringResource(R.string.red)) },
                        value = codeRed,
                        onValueChange = onUpdateRed,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color.Red,
                            unfocusedBorderColor = Color.Red,
                            focusedLabelColor = Color.Red,
                            unfocusedLabelColor = Color.Red
                        ),
                        modifier = Modifier
                            .width(80.dp)
                            .height(60.dp)
                            .padding(end = 16.dp)
                            .weight(1f)
                    )
                    OutlinedTextField(
                        label = { Text(stringResource(R.string.green)) },
                        value = codeGreen,
                        onValueChange = onUpdateGreen,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color.Green,
                            unfocusedBorderColor = Color.Green,
                            focusedLabelColor = Color.Green,
                            unfocusedLabelColor = Color.Green
                        ),
                        modifier = Modifier
                            .width(80.dp)
                            .height(60.dp)
                            .padding(end = 16.dp)
                            .weight(1f)
                    )
                    OutlinedTextField(
                        label = { Text(stringResource(R.string.blue)) },
                        value = codeBlue,
                        onValueChange = onUpdateBlue,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color.Blue,
                            unfocusedBorderColor = Color.Blue,
                            focusedLabelColor = Color.Blue,
                            unfocusedLabelColor = Color.Blue
                        ),
                        modifier = Modifier
                            .width(80.dp)
                            .height(60.dp)
                            .padding(end = 16.dp)
                            .weight(1f)
                    )
                }
            }
        }
    }
}

@Preview(showSystemUi = true, device = Devices.PHONE)
@Composable
fun PreviewScreen() {
    var encodedData by remember { mutableStateOf("") }
    var red by remember { mutableStateOf("0") }
    var green by remember { mutableStateOf("0") }
    var blue by remember { mutableStateOf("0") }

    QRCodeParamsScreen(
        encodedData = encodedData,
        onUpdateEncodedData = {encodedData = it},
        codeRed = red,
        codeGreen = green,
        codeBlue = blue,
        onUpdateRed = { red = it },
        onUpdateGreen = { green = it },
        onUpdateBlue = { blue = it },
        textColor = Color(red.toInt(), green.toInt(), blue.toInt()),
        onGenerateQRCode = {}
    )
}
