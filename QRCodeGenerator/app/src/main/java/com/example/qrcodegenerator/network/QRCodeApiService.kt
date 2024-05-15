package com.example.qrcodegenerator.network

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface QRCodeApiService {
    @GET("create-qr-code")
    suspend fun getQRCode(
        @Query("data") encodedData: String
    ): Response<ResponseBody>
}
