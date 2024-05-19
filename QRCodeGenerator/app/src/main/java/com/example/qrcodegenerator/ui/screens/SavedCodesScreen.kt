package com.example.qrcodegenerator.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.qrcodegenerator.R
import com.example.qrcodegenerator.data.GetSavedCodesStatus
import com.example.qrcodegenerator.ui.SavedCodesDataForScreen

@Composable
fun SavedCodesScreen(
    savedCodes: MutableList<SavedCodesDataForScreen>,
    savedCodesStatus: GetSavedCodesStatus,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    when (savedCodesStatus) {
        GetSavedCodesStatus.NOT_STARTED -> {}

        GetSavedCodesStatus.IN_PROGRESS -> {
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
                Text(
                    text = stringResource(R.string.please_wait),
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.bodyLarge,
                    fontSize = 30.sp
                )
                Image(
                    painter = painterResource(id = R.drawable.loading_img),
                    contentDescription = stringResource(R.string.loading_image),
                    modifier = Modifier.size(400.dp)
                )
            }
        }

        GetSavedCodesStatus.COMPLETED -> {
            if (savedCodes.isEmpty()) {
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
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        text = stringResource(R.string.no_qr_codes_have_been_saved),
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.weight(3f))
                }
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
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
                    items(items = savedCodes, key = null) {
                        item -> ExpandableCard(savedCode = item)
                    }
                }
            }
        }

        GetSavedCodesStatus.SERVER_ERROR -> {
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
                Text(
                    text = stringResource(R.string.unknown_error_text),
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.bodyLarge,
                    fontSize = 30.sp,
                    modifier = Modifier.padding(start = 16.dp, end = 16.dp)
                )
                Image(
                    painter = painterResource(id = R.drawable.ic_broken_image),
                    contentDescription = stringResource(R.string.unknown_error_text),
                    modifier = Modifier.size(400.dp)
                )
            }
        }

        GetSavedCodesStatus.UNAUTHORIZED -> {
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
                Text(
                    text = stringResource(R.string.you_are_not_logged_in_please_log_in_to_see_saved_qr_codes),
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.bodyLarge,
                    fontSize = 30.sp,
                    modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 300.dp)
                )
            }
        }
    }
}


@Composable
fun ExpandableCard(
    savedCode: SavedCodesDataForScreen
) {
    var expanded by remember { mutableStateOf (false) }

    Card(
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .clickable { expanded = !expanded }
    ) {
        if (!expanded) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .weight(2f)
                        .fillMaxSize()
                ) {
                    Text(
                        text = "Encoded data: ${savedCode.encodedData}",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                }

                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxSize()
                ) {
                    Image(
                        bitmap = savedCode.imageBitmap,
                        contentDescription = savedCode.encodedData,
                        modifier = Modifier
                            .size(80.dp)
                            .clip(RoundedCornerShape(12.dp))
                    )
                }
            }
        } else {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxSize()
            ) {
                Text(
                    lineHeight = 40.sp,
                    text = "Encoded data: ${savedCode.encodedData}",
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleLarge,
                    fontSize = 40.sp,
                    modifier = Modifier.padding(top = 8.dp, bottom = 16.dp)
                )
                Image(
                    bitmap = savedCode.imageBitmap,
                    contentDescription = savedCode.encodedData,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
            }
        }
    }
}


@Preview(showBackground = true, device = Devices.PHONE)
@Composable
fun SavedCodesScreenPreview() {
    SavedCodesScreen(
        savedCodes = mutableListOf(),
        savedCodesStatus = GetSavedCodesStatus.SERVER_ERROR
    )
}
