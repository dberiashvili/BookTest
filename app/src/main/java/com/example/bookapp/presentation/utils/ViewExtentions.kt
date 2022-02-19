package com.example.bookapp.presentation.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.View

fun View.hide() {
    this.visibility = View.GONE
}

fun View.show() {
    this.visibility = View.VISIBLE
}

fun Context.share(url: String) {
    val intent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT, url)
        type = "text/plain"
    }
    startActivity(intent)
}

fun Context.openUrl(url: String) {
    val intent = Intent(Intent.ACTION_VIEW).setData(Uri.parse(url))
    startActivity(intent)
}