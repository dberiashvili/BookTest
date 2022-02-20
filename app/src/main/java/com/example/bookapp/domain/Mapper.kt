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
       title, subtitle, isbn, price, image, url
    )
}

fun BookDetailsDTO.toDomain(): BookDetails {
    return BookDetails(
        title, subtitle, authors, publisher, isbn, pages, year, rating, desc, price, image, url
    )
}

fun BookEntity.toDomain(): Book {
    return Book(
        title, subtitle, isbn, price, image, url
    )
}

fun Book.toEntity(): BookEntity {
    return BookEntity(
        0, title, subtitle, isbn, price, image, url
    )
}