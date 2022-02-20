package com.example.bookapp.data.repository

import com.example.bookapp.data.db.BookDB
import com.example.bookapp.data.service.BookService
import com.example.bookapp.domain.models.Book
import com.example.bookapp.domain.models.BookDetails
import com.example.bookapp.domain.models.BookResponse
import com.example.bookapp.domain.repository.BooksRepository
import com.example.bookapp.domain.toDomain
import com.example.bookapp.domain.toEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class BooksRepoImpl @Inject constructor(private val service: BookService, private val bookDB: BookDB) : BooksRepository {
    override suspend fun getBooks(page: Int, query: String): BookResponse {
        return service.getBooks(page, query).toDomain()
    }

    override suspend fun getBooksDetails(isbn: String): BookDetails {
        return service.getBookDetails(isbn).toDomain()
    }

    override fun getSavedBooks(): Flow<List<Book>> {
        return bookDB.dao.getBooks().map { list ->
            list.map { entity ->
                entity.toDomain()
            }
        }
    }

    override suspend fun saveBook(book: Book) {
        return bookDB.dao.saveBook(book.toEntity())
    }

    override suspend fun deleteBook(isbn: String) {
        bookDB.dao.deleteFromFavorites(isbn)
    }

    override suspend fun bookExists(isbn: String): Book? {
        return bookDB.dao.bookExists(isbn)?.toDomain()
    }
}