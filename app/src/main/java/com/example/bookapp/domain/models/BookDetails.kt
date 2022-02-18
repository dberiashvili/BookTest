package com.example.bookapp.domain.models


data class BookDetails(
    val title: String,
    val subtitle: String,
    val authors: String,
    val publisher: String,
    val isbn: String,
    val pages: Int,
    val year: Int,
    val rating: Int,
    val desc: String,
    val price: String,
    val image: String,
    val url: String
)