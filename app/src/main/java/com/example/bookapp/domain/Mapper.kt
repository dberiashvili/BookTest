package com.example.bookapp.domain

import com.example.bookapp.data.db.BookEntity
import com.example.bookapp.data.models.BookDTO
import com.example.bookapp.data.models.BookDetailsDTO
import com.example.bookapp.data.models.BookResponseDTO
import com.example.bookapp.domain.models.Book
import com.example.bookapp.domain.models.BookResponse
import com.example.bookapp.domain.models.BookDetails

fun BookResponseDTO.toDomain(): BookResponse {
    return BookResponse(
        total = total,
        page = page,
        books = books.map {
            it.toDomain()
        }
    )
}

fun BookDTO.toDomain(): Book {
    return Book(
       id = 0, title, subtitle, isbn, price, image, url, isFavorite = false
    )
}

fun BookDetailsDTO.toDomain(): BookDetails {
    return BookDetails(
        title, subtitle, authors, publisher, isbn, pages, year, rating, desc, price, image, url
    )
}

fun BookEntity.toDomain(): Book {
    return Book(
       id, title, subtitle, isbn, price, image, url, isFavorite
    )
}

fun Book.toEntity(): BookEntity {
    return BookEntity(
        id, title, subtitle, isbn, price, image, url, isFavorite
    )
}