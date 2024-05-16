package com.example.qrcodegenerator.data

import com.example.qrcodegenerator.network.AuthApiService
import com.example.qrcodegenerator.network.DatabaseApiService
import com.example.qrcodegenerator.network.QRCodeApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface AppContainer {
    val authRepository: AuthRepository
    val qrCodeRepository: QRCodeRepository
    val databaseRepository: DatabaseRepository
}

class DefaultAppContainer: AppContainer {
    private val authUrl = "http://10.0.2.2:8089"
    private val qrCodeUrl = "https://api.qrserver.com/v1/"
    private val databaseUrl = "http://10.0.2.2:12345"

    private val authRetrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(authUrl)
        .build()

    private val qrCodeRetrofit: Retrofit = Retrofit.Builder()
        .baseUrl(qrCodeUrl)
        .build()

    private val databaseRetrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(databaseUrl)
        .build()

    private val authRetrofitService: AuthApiService by lazy {
        authRetrofit.create(AuthApiService::class.java)
    }

    private val qrCodeRetrofitService: QRCodeApiService by lazy {
        qrCodeRetrofit.create(QRCodeApiService::class.java)
    }

    private val databaseRetrofitService: DatabaseApiService by lazy {
        databaseRetrofit.create(DatabaseApiService::class.java)
    }

    override val authRepository: AuthRepository by lazy {
        NetworkAuthRepository(authRetrofitService)
    }

    override val qrCodeRepository: QRCodeRepository by lazy {
        NetworkQRCodeRepository(qrCodeRetrofitService)
    }

    override val databaseRepository: DatabaseRepository by lazy {
        NetworkDatabaseRepository(databaseRetrofitService)
    }
}
