package com.example.qrcodegenerator.data

import com.example.qrcodegenerator.network.QRCodeApiService
import okhttp3.ResponseBody
import retrofit2.Response

interface QRCodeRepository {
    suspend fun getQRCode(
        encodedData: String
    ): Response<ResponseBody>
}

class NetworkQRCodeRepository(
    private val qrCodeApiService: QRCodeApiService
): QRCodeRepository {
    override suspend fun getQRCode(
        encodedData: String
    ): Response<ResponseBody> = qrCodeApiService.getQRCode(encodedData)
}
