package com.example.qrcodegenerator.model

import kotlinx.serialization.Serializable

@Serializable
data class AuthServiceBasicResponse(
    val message: String
)

@Serializable
data class UserCredentials(
    val username: String,
    val password: String
)

@Serializable
data class LoginResponse(
    val token: String
)
