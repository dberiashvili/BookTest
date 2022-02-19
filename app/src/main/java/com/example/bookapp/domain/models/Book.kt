package com.example.bookapp.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class Book(
    val title: String,
    val subtitle: String,
    val isbn: String,
    val price: String,
    val image: String,
    val url: String,
    var isFavorite: Boolean = false
):Parcelable
