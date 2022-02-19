package com.example.bookapp.domain.repository

import com.example.bookapp.domain.models.Book
import com.example.bookapp.domain.models.BookResponse
import com.example.bookapp.domain.models.BookDetails
import kotlinx.coroutines.flow.Flow

interface BooksRepository {
    suspend fun getBooks(page: Int, query: String): BookResponse
    suspend fun getBooksDetails(isbn: String): BookDetails
    suspend fun saveBook(book:Book)
    fun getSavedBooks(): Flow<List<Book>>
    suspend fun deleteBook(book: Book)
    suspend fun getBookById(id: Int): Book?
}