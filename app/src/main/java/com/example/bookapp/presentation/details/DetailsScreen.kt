package com.example.bookapp.presentation.details

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
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
import com.example.bookapp.presentation.utils.hide
import com.example.bookapp.presentation.utils.show
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

        binding.openInBrowserButton.setOnClickListener {
            openBookInBrowser(args.book.url)
        }



        binding.favButton.setOnClickListener {
            if (!args.book.isFavorite) {
                binding.favButton.setImageResource(R.drawable.ic_baseline_favorite_24)

                viewModel.saveBook(args.book)
            } else {
                binding.favButton.setImageResource(R.drawable.ic_baseline_favorite_border_24)
                viewModel.deleteBookFromFavorites(args.book)
                args.book.isFavorite = false

            }
        }

        viewModel.searchBookById(args.book.id)
        lifecycleScope.launchWhenStarted {
            viewModel.searchedBook.collectLatest {
                if (it!!.isFavorite) {
                    binding.favButton.setImageResource(R.drawable.ic_baseline_favorite_24)
                } else {
                    binding.favButton.setImageResource(R.drawable.ic_baseline_favorite_border_24)
                }
            }
        }
        lifecycleScope.launchWhenStarted {
            viewModel.bookDetails.collectLatest {
                when (it) {
                    is Resource.Loading -> {
                        binding.progressBar.show()
                    }
                    is Resource.Success -> {
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
                        binding.progressBar.show()
                    }
                }
            }
        }
        return binding.root
    }

    private fun openBookInBrowser(uri: String) {
        val intent = Intent(Intent.ACTION_VIEW).setData(Uri.parse(uri))
        startActivity(intent)
    }

}