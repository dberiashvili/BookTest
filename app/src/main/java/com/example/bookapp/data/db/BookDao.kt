package com.example.bookapp.data.db

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface BookDao {
    @Query("SELECT * FROM favourite_books ORDER BY title")
    fun getBooks(): Flow<List<BookEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveBook(note: BookEntity)

    @Delete
    suspend fun deleteFromFavorites(bookEntity: BookEntity)

    @Query("SELECT * FROM favourite_books WHERE isbn = :isbn LIMIT 1")
    suspend fun bookExists(isbn: String): BookEntity?
}