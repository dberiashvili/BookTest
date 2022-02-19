package com.example.bookapp.domain.usecase

import com.example.bookapp.domain.repository.BooksRepository
import javax.inject.Inject

class SearchBookByIdUseCase @Inject constructor(private val repository: BooksRepository) {
    suspend operator fun invoke(id: Int) = repository.getBookById(id)
}