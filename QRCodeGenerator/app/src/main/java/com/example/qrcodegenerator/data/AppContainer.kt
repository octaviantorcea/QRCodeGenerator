package com.example.qrcodegenerator.data

import com.example.qrcodegenerator.network.AuthApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface AppContainer {
    val authRepository: AuthRepository
}

class DefaultAppContainer: AppContainer {
    private val authUrl = "http://127.0.0.1"

    private val authRetrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(authUrl)
        .build()

    private val authRetrofitService: AuthApiService by lazy {
        authRetrofit.create(AuthApiService::class.java)
    }

    override val authRepository: AuthRepository by lazy {
        NetworkAuthRepository(authRetrofitService)
    }
}
