package com.example.qrcodegenerator.model

import kotlinx.serialization.Serializable

@Serializable
data class AuthServiceBasicResponse(
    val message: String
)

@Serializable
data class NewUser(
    val username: String,
    val password: String
)
