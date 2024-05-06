package com.example.qrcodegenerator.network

import com.example.qrcodegenerator.model.AuthServiceBasicResponse
import com.example.qrcodegenerator.model.LoginResponse
import com.example.qrcodegenerator.model.UserCredentials
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApiService {
    @POST("register")
    suspend fun register(
        @Body newUser: UserCredentials
    ): Response<AuthServiceBasicResponse>

    @POST("login")
    suspend fun login(
        @Body user: UserCredentials
    ) : Response<LoginResponse>
}
