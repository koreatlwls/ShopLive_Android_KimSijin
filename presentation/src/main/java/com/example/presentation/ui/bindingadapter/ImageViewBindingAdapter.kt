package com.example.presentation.ui.bindingadapter

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("imageUrl", "placeholder")
fun ImageView.loadImage(url: String, placeholder: Drawable) {
    Glide.with(this.context)
        .load(url)
        .placeholder(placeholder)
        .fallback(placeholder)
        .error(placeholder)
        .centerCrop()
        .into(this)
}