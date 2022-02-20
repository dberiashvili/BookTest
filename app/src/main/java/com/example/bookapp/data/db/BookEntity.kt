package com.example.bookapp.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favourite_books")
data class BookEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val title: String,
    val subtitle: String,
    val isbn: String,
    val price: String,
    val image: String,
    val url: String
)