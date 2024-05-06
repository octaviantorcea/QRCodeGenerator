package com.example.qrcodegenerator.network

import com.example.qrcodegenerator.model.AuthServiceBasicResponse
import com.example.qrcodegenerator.model.NewUser
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApiService {
    @POST("register")
    suspend fun register(
        @Body newUser: NewUser
    ): Response<AuthServiceBasicResponse>
}
