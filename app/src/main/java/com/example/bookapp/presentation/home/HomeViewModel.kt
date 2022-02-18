package com.example.bookapp.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bookapp.domain.models.BookResponse
import com.example.bookapp.domain.models.Resource
import com.example.bookapp.domain.usecase.FetchBooksUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val fetchBooksUseCase: FetchBooksUseCase
) : ViewModel() {
    val query = MutableStateFlow("Android")
    var page = 1
    private val _bookResponse: MutableLiveData<Resource<BookResponse>> =
        MutableLiveData()
    val getBooks: LiveData<Resource<BookResponse>> = _bookResponse
    fun getBooksResponse(query: String, page: Int) {
        viewModelScope.launch {
            _bookResponse.postValue(Resource.Loading())
            try {
                val result = fetchBooksUseCase.invoke(page, query)
                _bookResponse.postValue(Resource.Success(result))
            } catch (e: Exception) {
                _bookResponse.postValue(Resource.Error(message = e.message!!))
            }
        }
    }

    fun fetchNextPage() {
        viewModelScope.launch {
            _bookResponse.postValue(Resource.Loading())
            try {
                val result = fetchBooksUseCase.invoke(++page, query.value)
                _bookResponse.postValue(Resource.Success(result))
            } catch (e: Exception) {
                _bookResponse.postValue(Resource.Error(message = e.message!!))
            }
        }
    }
}