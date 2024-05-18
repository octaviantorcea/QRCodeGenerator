package com.example.qrcodegenerator.data

import com.example.qrcodegenerator.model.DatabaseSaveCodeBody
import com.example.qrcodegenerator.model.GetCodesResponse
import com.example.qrcodegenerator.model.SaveCodeBody
import com.example.qrcodegenerator.network.DatabaseApiService
import okhttp3.ResponseBody
import retrofit2.Response

interface DatabaseRepository {
    suspend fun saveQRCode(
        authToken: String,
        encodedStringImage: String,
        encodedData: String,
        qrCodeColor: String
    ): Response<SaveCodeBody>

    suspend fun getQRCodes(
        authToken: String
    ): Response<GetCodesResponse>
}

class NetworkDatabaseRepository(
    private val databaseApiService: DatabaseApiService
): DatabaseRepository {
    override suspend fun saveQRCode(
        authToken: String,
        encodedStringImage: String,
        encodedData: String,
        qrCodeColor: String
    ): Response<SaveCodeBody> = databaseApiService.saveQRCode(
        authToken,
        DatabaseSaveCodeBody(encodedStringImage, encodedData, qrCodeColor)
    )

    override suspend fun getQRCodes(
        authToken: String
    ): Response<GetCodesResponse> = databaseApiService.getQRCodes(authToken)
}
