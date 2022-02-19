package com.example.bookapp.presentation.utils

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class AdapterScrollListener(
    private val layoutManager: GridLayoutManager,
    private val onNext: () -> Unit
) : RecyclerView.OnScrollListener() {

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        if (dy > 0 && layoutManager.findLastCompletelyVisibleItemPosition() + 1 == layoutManager.itemCount) {
            onNext()
        }
    }
}