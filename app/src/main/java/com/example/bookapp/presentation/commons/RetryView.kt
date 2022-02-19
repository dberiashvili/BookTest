package com.example.bookapp.presentation.commons

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.bookapp.databinding.RetryViewBinding

class RetryView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) :
    ConstraintLayout(context, attrs, defStyleAttr) {
    private val binding = RetryViewBinding.inflate(LayoutInflater.from(context), this, true)

    var retry: (() -> Unit)? = null
    fun setErrorMessage(message: String) {
        binding.errorText.text = message
    }

    init {
        binding.retryButton.setOnClickListener {
            retry?.invoke()
        }
    }


}