package com.example.qrcodegenerator.data

import com.example.qrcodegenerator.network.AuthApiService

interface AuthRepository {

}

class NetworkAuthRepository(
    private val authApiService: AuthApiService
) : AuthRepository {

}
