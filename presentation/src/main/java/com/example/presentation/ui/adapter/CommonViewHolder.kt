package com.example.presentation.ui.adapter

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.example.presentation.databinding.ItemEmptyBinding
import com.example.presentation.databinding.ItemErrorBinding
import com.example.presentation.databinding.ItemLoadingBinding
import com.example.presentation.databinding.ItemMarvelCharacterBinding
import com.example.presentation.model.CommonItem
import com.example.presentation.model.ViewObject

sealed class CommonViewHolder(
    binding: ViewDataBinding
) : RecyclerView.ViewHolder(binding.root) {

    abstract fun bind(item: CommonItem)

    class LoadingViewHolder(binding: ItemLoadingBinding) : CommonViewHolder(binding) {
        override fun bind(item: CommonItem) = Unit
    }

    class EmptyViewHolder(binding: ItemEmptyBinding) : CommonViewHolder(binding) {
        override fun bind(item: CommonItem) = Unit
    }

    class SuccessViewHolder(private val binding: ItemMarvelCharacterBinding) : CommonViewHolder(binding) {
        override fun bind(item: CommonItem) {
            if (item.viewObject is ViewObject.SuccessViewObject) {
                binding.marvelCharacter = item.viewObject.marvelCharacter
                binding.executePendingBindings()
            }
        }
    }

    class ErrorViewHolder(binding: ItemErrorBinding) : CommonViewHolder(binding) {
        override fun bind(item: CommonItem) = Unit
    }

}