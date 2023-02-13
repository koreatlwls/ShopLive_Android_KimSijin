package com.example.presentation.ui.bindingadapter

import android.view.View
import androidx.databinding.BindingAdapter
import com.example.presentation.model.UiState
import com.google.android.material.button.MaterialButton

@BindingAdapter("showError")
fun MaterialButton.showError(uiState: UiState) {
    visibility = if (uiState == UiState.Error)
        View.VISIBLE
    else
        View.GONE
}