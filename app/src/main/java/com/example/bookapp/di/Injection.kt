package com.example.bookapp.di

import android.content.Context
import androidx.room.Room
import com.example.bookapp.data.db.BookDB
import com.example.bookapp.data.db.BookDao
import com.example.bookapp.data.repository.BooksRepoImpl
import com.example.bookapp.data.service.BookService
import com.example.bookapp.domain.repository.BooksRepository
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class Injection {
    private val json: Json = Json {
        ignoreUnknownKeys = true
    }

    @OptIn(ExperimentalSerializationApi::class)
    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .baseUrl("https://api.itbook.store/1.0/")
            .build()
    }

    @Singleton
    @Provides
    fun providesHttpLoggingInterceptor() = HttpLoggingInterceptor()
        .apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

    @Singleton
    @Provides
    fun providesOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient =
        OkHttpClient
            .Builder()
            .addInterceptor(httpLoggingInterceptor)
            .build()

    @Provides
    fun provideService(retrofit: Retrofit): BookService = retrofit.create(BookService::class.java)

    @Provides
    fun provideBookDao(database: BookDB): BookDao {
        return database.dao
    }

    @Provides
    @Singleton
    fun provideBookDataBase(@ApplicationContext appContext: Context): BookDB {
        return Room.databaseBuilder(
            appContext,
            BookDB::class.java,
            "book_db"
        ).build()
    }

    @Singleton
    @Provides
    fun provideRepo(service: BookService, bookDB: BookDB): BooksRepository = BooksRepoImpl(service, bookDB)
}