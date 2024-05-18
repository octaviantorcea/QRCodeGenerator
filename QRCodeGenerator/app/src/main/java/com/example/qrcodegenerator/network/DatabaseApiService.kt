package com.example.qrcodegenerator.network

import com.example.qrcodegenerator.model.DatabaseSaveCodeBody
import com.example.qrcodegenerator.model.GetCodesResponse
import com.example.qrcodegenerator.model.SaveCodeBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface DatabaseApiService {
    @POST("save")
    suspend fun saveQRCode(
        @Header("Authorization") authToken: String,
        @Body saveCodeBody: DatabaseSaveCodeBody
    ): Response<SaveCodeBody>

    @GET("codes")
    suspend fun getQRCodes(
        @Header("Authorization") authToken: String,
    ): Response<GetCodesResponse>
}
