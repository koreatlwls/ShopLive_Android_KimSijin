package com.example.presentation.ui.bindingadapter

import android.view.View
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.presentation.ui.search.UiState

@BindingAdapter("showSuccess")
fun RecyclerView.showSuccess(uiState: UiState) {
    visibility = if (uiState == UiState.Success)
        View.VISIBLE
    else
        View.GONE
}

@BindingAdapter("showList")
fun <T>RecyclerView.showList(list : List<T>) {
    if(list.isNotEmpty() && adapter is ListAdapter<*, *>){
        visibility = View.VISIBLE
        (adapter as ListAdapter<T, *>).submitList(list)
    }else{
        visibility = View.GONE
    }
}