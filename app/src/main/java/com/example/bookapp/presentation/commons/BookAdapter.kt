package com.example.bookapp.presentation.commons

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bookapp.databinding.BookLayoutBinding
import com.example.bookapp.domain.models.Book

class BookAdapter : RecyclerView.Adapter<BookAdapter.BookViewHolder>() {
    val books = mutableListOf<Book>()
    var onClick: ((Book) -> Unit)? = null

    inner class BookViewHolder(private val binding: BookLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            val book = books[position]
            binding.title.text = book.title
            binding.subtitle.text = book.subtitle
            binding.price.text = book.price
            Glide.with(binding.root).load(book.image).centerCrop()
                .into(binding.cover)
            binding.root.setOnClickListener {
                onClick?.invoke(book)
            }

        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val binding = BookLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BookViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int = books.size

    fun addBooks(list: List<Book>) {
        books.addAll(list)
        notifyDataSetChanged()
    }

    fun clearList() {
        books.clear()
        notifyDataSetChanged()
    }
}