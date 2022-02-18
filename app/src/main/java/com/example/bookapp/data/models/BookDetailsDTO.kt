package com.example.bookapp.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BookDetailsDTO(
    val title: String,
    val subtitle: String,
    val authors: String,
    val publisher: String,
    @SerialName("isbn13")
    val isbn: String,
    val pages: Int,
    val year: Int,
    val rating: Int,
    val desc: String,
    val price: String,
    val image: String,
    val url: String
)
