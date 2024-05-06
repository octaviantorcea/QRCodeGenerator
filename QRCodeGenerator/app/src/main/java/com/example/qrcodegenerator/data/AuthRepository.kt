package com.example.qrcodegenerator.data

import com.example.qrcodegenerator.model.AuthServiceBasicResponse
import com.example.qrcodegenerator.model.LoginResponse
import com.example.qrcodegenerator.model.UserCredentials
import com.example.qrcodegenerator.network.AuthApiService
import retrofit2.Response

interface AuthRepository {
    suspend fun register(
        username: String,
        password: String
    ): Response<AuthServiceBasicResponse>

    suspend fun login(
        username: String,
        password: String
    ): Response<LoginResponse>
}

class NetworkAuthRepository(
    private val authApiService: AuthApiService
) : AuthRepository {
    override suspend fun register(
        username: String,
        password: String
    ) : Response<AuthServiceBasicResponse> = authApiService.register(UserCredentials(username, password))

    override suspend fun login(
        username: String,
        password: String
    ): Response<LoginResponse> = authApiService.login(UserCredentials(username, password))
}
