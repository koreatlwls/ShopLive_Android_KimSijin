package com.example.presentation.ui.bindingadapter

import android.view.View
import androidx.databinding.BindingAdapter
import com.example.presentation.model.UiState
import com.google.android.material.progressindicator.CircularProgressIndicator

@BindingAdapter("showOnLoading")
fun CircularProgressIndicator.showOnLoading(uiState: UiState) {
    visibility = if (uiState == UiState.Loading)
        View.VISIBLE
    else
        View.GONE
}