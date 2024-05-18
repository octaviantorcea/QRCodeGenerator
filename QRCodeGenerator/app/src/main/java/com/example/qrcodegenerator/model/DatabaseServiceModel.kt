package com.example.qrcodegenerator.model

import kotlinx.serialization.SerialName
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

@Serializable
data class GetCodesResponseEntry(
    @SerialName(value = "encoded_data")
    val encodedData: String,
    @SerialName(value = "string_image")
    val stringImage: String
)

@Serializable
data class GetCodesResponse(
    @SerialName(value = "saved_codes")
    val savedCodes: List<GetCodesResponseEntry>
)
