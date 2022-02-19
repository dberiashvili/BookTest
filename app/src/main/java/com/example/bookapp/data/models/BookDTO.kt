package com.example.bookapp.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BookDTO(
    val title: String,
    val subtitle: String,
    @SerialName("isbn13")
    val isbn: String,
    val price: String,
    val image: String,
    val url: String,
)

