package com.example.bookapp.presentation.details

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
                        binding.root.setOnClickListener {
                            viewModel.saveBook(args.book)
                        }
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