package com.example.bookapp.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.bookapp.R
import com.example.bookapp.databinding.FragmentHomeScreenBinding
import com.example.bookapp.domain.models.Resource
import com.example.bookapp.presentation.commons.BookAdapter
import com.example.bookapp.presentation.utils.AdapterScrollListener
import com.example.bookapp.presentation.utils.hide
import com.example.bookapp.presentation.utils.show
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


@AndroidEntryPoint
class HomeScreen : Fragment(R.layout.fragment_home_screen) {
    private val viewModel: HomeViewModel by viewModels()
    lateinit var binding: FragmentHomeScreenBinding
    private val bookAdapter = BookAdapter()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeScreenBinding.inflate(layoutInflater)
        val gridLayoutManager = GridLayoutManager(context, 2)
        binding.booksRV.addOnScrollListener(
            AdapterScrollListener(
                gridLayoutManager,
                onNext = { viewModel.fetchNextPage() })
        )
        binding.booksRV.apply {
            layoutManager = gridLayoutManager
            adapter = bookAdapter
        }
        viewModel.getBooksResponse(viewModel.query.value, viewModel.page)
        lifecycleScope.launchWhenStarted {
            viewModel.getBooks.collect {
                when (it) {
                    is Resource.Success -> {
                        binding.booksRV.show()
                        binding.retryView.hide()
                        val books = it.data!!.books
                        books.map {
                            bookAdapter.onClick = {
                                val action =
                                    HomeScreenDirections.actionHomeScreenToDetailsScreen(it)
                                findNavController().navigate(action)
                            }
                        }
                        binding.loading.hide()
                        bookAdapter.addBooks(books)


                    }

                    is Resource.Loading -> {
                        binding.retryView.hide()
                        binding.loading.show()
                        binding.booksRV.hide()
                    }

                    is Resource.Error -> {
                        bookAdapter.clearList()
                        binding.loading.hide()
                        binding.retryView.show()
                        binding.retryView.setErrorMessage(it.message!!)
                        binding.retryView.retry = {
                            viewModel.getBooksResponse(viewModel.query.value, viewModel.page)
                        }
                    }
                }
            }
        }

        var job: Job? = null
        binding.searchET.addTextChangedListener { editable ->
            job?.cancel()
            job = MainScope().launch {
                delay(500)

                if (editable.toString().length > 2) {
                    bookAdapter.clearList()
                    viewModel.query.value = editable.toString()
                    viewModel.page = 0
                    viewModel.getBooksResponse(viewModel.query.value, viewModel.page)
                    lifecycleScope.launchWhenStarted {
                        viewModel.getBooks.collect {
                            when (it) {
                                is Resource.Success -> {
                                    binding.loading.visibility = View.GONE
                                    val books = it.data!!.books
                                    books.map {
                                        bookAdapter.onClick = {
                                            val action =
                                                HomeScreenDirections.actionHomeScreenToDetailsScreen(
                                                    it
                                                )
                                            findNavController().navigate(action)
                                        }
                                    }
                                    bookAdapter.addBooks(books)
                                }
                            }
                        }
                    }
                }
            }

        }
        return binding.root
    }

}