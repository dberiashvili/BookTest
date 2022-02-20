package com.example.bookapp.domain.usecase

import com.example.bookapp.domain.repository.BooksRepository
import javax.inject.Inject

class SearchBookByIsbnUseCase @Inject constructor(private val repository: BooksRepository) {
    suspend operator fun invoke(sbn: String) = repository.bookExists(sbn)
}