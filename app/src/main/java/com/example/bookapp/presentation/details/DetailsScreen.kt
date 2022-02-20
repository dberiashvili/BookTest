package com.example.bookapp.presentation.details

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.bookapp.R
import com.example.bookapp.databinding.FragmentDetailsScreenBinding
import com.example.bookapp.domain.models.Resource
import com.example.bookapp.presentation.utils.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest


@AndroidEntryPoint
class DetailsScreen : Fragment(R.layout.fragment_details_screen) {
    private val viewModel: DetailsViewModel by viewModels()
    private val args: DetailsScreenArgs by navArgs()
    private lateinit var binding: FragmentDetailsScreenBinding


    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailsScreenBinding.inflate(layoutInflater)
        viewModel.getBooksDetails(args.book.isbn)
        binding.backArrow.setOnClickListener {
            findNavController().navigate(DetailsScreenDirections.actionDetailsScreenToHomeScreen())
        }
        viewModel.searchByIsbn(args.book.isbn)

        binding.shareButton.setOnClickListener {
            context?.share(args.book.url)
        }

        binding.openInBrowserButton.setOnClickListener {
            context?.openUrl(args.book.url)
        }



        binding.favButton.setOnClickListener {
            viewModel.searchedBook.observeOnce(viewLifecycleOwner) { book ->
                if (book != null) {
                    viewModel.deleteBookFromFavorites(book.isbn)
                    viewModel.searchByIsbn(args.book.isbn)
                    binding.favButton.setImageResource(R.drawable.ic_baseline_favorite_border_24)
                } else {
                    viewModel.saveBook(args.book)
                    viewModel.searchByIsbn(args.book.isbn)
                    binding.favButton.setImageResource(R.drawable.ic_baseline_favorite_24)
                }
            }
        }

        viewModel.searchedBook.observe(viewLifecycleOwner) {
            if (it !== null) {
                binding.favButton.setImageResource(R.drawable.ic_baseline_favorite_24)
            } else {
                binding.favButton.setImageResource(R.drawable.ic_baseline_favorite_border_24)
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.bookDetails.collectLatest {
                when (it) {
                    is Resource.Loading -> {
                        binding.progressBar.show()
                    }
                    is Resource.Success -> {
                        binding.retryView.hide()
                        val response = it.data!!
                        binding.progressBar.hide()
                        Glide.with(requireContext()).load(response.image)
                            .centerCrop()
                            .into(binding.poster)
                        binding.authorTV.text = response.authors
                        binding.publisherTV.text = response.publisher
                        binding.ratingBar.rating = response.rating.toFloat()
                        binding.yearTV.text = "Published in ${response.year}"
                        binding.description.text = response.desc
                    }
                    is Resource.Error -> {
                        binding.progressBar.hide()
                        binding.retryView.show()
                        binding.retryView.setErrorMessage(it.message!!)
                        binding.retryView.retry = {
                            viewModel.getBooksDetails(args.book.isbn)
                        }
                    }
                }
            }
        }
        return binding.root
    }

}