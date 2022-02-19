package com.example.bookapp.domain.usecase

import com.example.bookapp.domain.models.Book
import com.example.bookapp.domain.repository.BooksRepository
import javax.inject.Inject

class DeleteBookFromFavoritesUseCase @Inject constructor(private val repository: BooksRepository) {
    suspend operator fun invoke(book: Book) =  repository.deleteBook(book)
}