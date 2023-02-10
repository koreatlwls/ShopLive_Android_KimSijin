package com.example.presentation.ui.bindingadapter

import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.presentation.ui.search.UiState

@BindingAdapter("showEmpty")
fun TextView.showEmpty(uiState: UiState) {
    visibility = if (uiState == UiState.Empty)
        View.VISIBLE
    else
        View.GONE
}