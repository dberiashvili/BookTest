package com.example.bookapp.data.models

import kotlinx.serialization.Serializable

@Serializable
data class BookResponseDTO(
    val total: String,
    val page: Int,
    val books: List<BookDTO>
)
