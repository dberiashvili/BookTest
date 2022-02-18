package com.example.bookapp.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class BookResponse(
    val total: String,
    val page: Int,
    val books: List<Book>
)
