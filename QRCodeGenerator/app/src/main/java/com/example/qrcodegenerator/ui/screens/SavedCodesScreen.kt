package com.example.qrcodegenerator.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
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
        GetSavedCodesStatus.NOT_STARTED -> TODO()

        GetSavedCodesStatus.IN_PROGRESS -> {
            Text(text = "zulul hahahahaha")
        }

        GetSavedCodesStatus.COMPLETED -> {
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
                    bitmap = savedCodes[2].imageBitmap,
                    contentDescription = "hai ca da",
                    modifier = Modifier
                        .padding(bottom = 16.dp)
                )

                Text(text = savedCodes[2].encodedData)
            }
        }
    }
}
