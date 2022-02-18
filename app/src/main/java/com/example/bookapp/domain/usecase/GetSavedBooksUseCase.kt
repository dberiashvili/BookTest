package com.example.bookapp.domain.usecase

import com.example.bookapp.domain.repository.BooksRepository
import javax.inject.Inject

class GetSavedBooksUseCase @Inject constructor(private val repository: BooksRepository) {
    operator fun invoke() = repository.getSavedBooks()
}