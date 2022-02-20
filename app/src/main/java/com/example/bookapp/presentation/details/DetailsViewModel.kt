package com.example.bookapp.presentation.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bookapp.domain.models.Book
import com.example.bookapp.domain.models.BookDetails
import com.example.bookapp.domain.models.Resource
import com.example.bookapp.domain.usecase.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val getBookDetailsUseCase: GetBookDetailsUseCase,
    private val saveBookUseCase: SaveBookUseCase,
    private val searchBookByIsbnUseCase: SearchBookByIsbnUseCase,
    private val deleteBookFromFavoritesUseCase: DeleteBookFromFavoritesUseCase,
) :
    ViewModel() {
    private val errorMessage = MutableStateFlow("")
    private val _searchedBook = MutableLiveData<Book?>()
    val searchedBook: LiveData<Book?> = _searchedBook
    private val _bookDetails: MutableStateFlow<Resource<BookDetails>> =
        MutableStateFlow(Resource.Loading())
    val bookDetails: StateFlow<Resource<BookDetails>> = _bookDetails
    fun getBooksDetails(isbn: String) {
        viewModelScope.launch {
            try {
                val response = getBookDetailsUseCase.invoke(isbn)
                _bookDetails.emit(Resource.Success(response))
            } catch (e: Exception) {
                _bookDetails.emit(Resource.Error(message = e.message!!))
            }

        }
    }

    fun saveBook(book: Book) {
        viewModelScope.launch {
            try {
                saveBookUseCase.invoke(book)
            } catch (e: Exception) {
                errorMessage.value = e.message ?: "Could not save to favorites"
            }
        }
    }

    fun deleteBookFromFavorites(isbn: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                deleteBookFromFavoritesUseCase.invoke(isbn)
            } catch (e: Exception) {
                errorMessage.value = e.message ?: "Could not delete from favorites"
            }
        }
    }

    fun searchByIsbn(isbn: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _searchedBook.postValue(searchBookByIsbnUseCase.invoke(isbn))
        }
    }


}