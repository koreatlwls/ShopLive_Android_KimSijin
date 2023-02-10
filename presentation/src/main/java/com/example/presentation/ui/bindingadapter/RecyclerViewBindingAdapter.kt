package com.example.presentation.ui.bindingadapter

import android.view.View
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.presentation.ui.search.UiState

@BindingAdapter("showSuccess")
fun RecyclerView.showSuccess(uiState: UiState) {
    visibility = if (uiState is UiState.Success)
        View.VISIBLE
    else
        View.GONE
}