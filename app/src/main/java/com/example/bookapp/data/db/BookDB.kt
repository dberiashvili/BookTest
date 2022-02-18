package com.example.bookapp.data.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [BookEntity::class], version = 1)
abstract class BookDB : RoomDatabase() {
    abstract val dao: BookDao
}