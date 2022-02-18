package com.example.bookapp.presentation.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bookapp.domain.models.Book
import com.example.bookapp.domain.usecase.GetSavedBooksUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(private val getSavedBooksUseCase: GetSavedBooksUseCase) :
    ViewModel() {
    init {
        getSavedBooks()
    }
    private val _savedBooks: MutableStateFlow<List<Book>> = MutableStateFlow(emptyList())
    val savedBooks: StateFlow<List<Book>> = _savedBooks
    private fun getSavedBooks() {
        viewModelScope.launch {
            try {
                getSavedBooksUseCase.invoke().collect {
                    _savedBooks.emit(it)
                }
            } catch (e: Exception) {
                _savedBooks.emit(emptyList())
            }
        }
    }

}