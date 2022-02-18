package com.example.bookapp.domain.usecase

import com.example.bookapp.domain.repository.BooksRepository
import javax.inject.Inject

class FetchBooksUseCase @Inject constructor(private val repository: BooksRepository) {
    suspend operator fun invoke(page: Int, query: String) = repository.getBooks(page, query)
}