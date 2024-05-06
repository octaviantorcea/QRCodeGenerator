package com.example.qrcodegenerator.data

import com.example.qrcodegenerator.model.AuthServiceBasicResponse
import com.example.qrcodegenerator.model.NewUser
import com.example.qrcodegenerator.network.AuthApiService
import retrofit2.Response

interface AuthRepository {
    suspend fun register(
        username: String,
        password: String
    ): Response<AuthServiceBasicResponse>
}

class NetworkAuthRepository(
    private val authApiService: AuthApiService
) : AuthRepository {
    override suspend fun register(
        username: String,
        password: String
    ) : Response<AuthServiceBasicResponse> = authApiService.register(NewUser(username, password))
}
