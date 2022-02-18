package com.example.bookapp.data.service

import com.example.bookapp.data.models.BookDetailsDTO
import com.example.bookapp.data.models.BookResponseDTO
import retrofit2.http.GET
import retrofit2.http.Path

interface BookService {
    @GET("search/{query}/{page}")
    suspend fun getBooks(
        @Path("page") page: Int,
        @Path("query") query: String
    ): BookResponseDTO

    @GET("books/{isbn}")
    suspend fun getBookDetails(@Path("isbn") isbn: String): BookDetailsDTO
}