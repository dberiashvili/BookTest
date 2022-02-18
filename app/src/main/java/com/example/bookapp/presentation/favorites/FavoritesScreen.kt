package com.example.bookapp.presentation.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.bookapp.R
import com.example.bookapp.databinding.FragmentFavoritesScreenBinding
import com.example.bookapp.presentation.commons.BookAdapter
import com.example.bookapp.presentation.utils.hide
import com.example.bookapp.presentation.utils.show
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class FavoritesScreen : Fragment(R.layout.fragment_favorites_screen) {
    lateinit var binding: FragmentFavoritesScreenBinding
    private val bookAdapter = BookAdapter()
    private val viewModel: FavoritesViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoritesScreenBinding.inflate(layoutInflater)
        binding.savedBooksRV.apply {
            adapter = bookAdapter
            layoutManager = GridLayoutManager(context, 2)
        }
        lifecycleScope.launchWhenStarted {
            viewModel.savedBooks.collect {
                if (it.isEmpty()) {
                    binding.emptyTV.show()
                } else {
                    binding.emptyTV.hide()
                }
                it.map {
                    bookAdapter.onClick = { book ->
                        findNavController().navigate(
                            FavoritesScreenDirections.actionFavoritesScreenToDetailsScreen(
                                book
                            )
                        )
                    }
                }
                bookAdapter.addBooks(it)
            }
        }

        return binding.root
    }
}
