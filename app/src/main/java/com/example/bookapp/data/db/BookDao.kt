package com.example.bookapp.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface BookDao {
    @Query("SELECT * FROM favourite_books ORDER BY title")
    fun getBooks(): Flow<List<BookEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveBook(note: BookEntity)

    @Query("DELETE FROM favourite_books WHERE isbn = :isbn")
    fun deleteFromFavorites(isbn: String)

    @Query("SELECT * FROM favourite_books WHERE isbn = :isbn LIMIT 1")
    suspend fun bookExists(isbn: String): BookEntity?
}