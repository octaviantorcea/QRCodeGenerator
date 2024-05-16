package com.example.qrcodegenerator.model

import kotlinx.serialization.Serializable

@Serializable
data class DatabaseSaveCodeBody(
    val encodedStringImage: String,
    val encodedData: String,
    val qrCodeColor: String
)

@Serializable
data class SaveCodeBody(
    val message: String
)
