package com.example.presentation.ui.search

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.example.presentation.databinding.ItemEmptyBinding
import com.example.presentation.databinding.ItemErrorBinding
import com.example.presentation.databinding.ItemLoadingBinding
import com.example.presentation.databinding.ItemMarvelCharacterBinding
import com.example.presentation.model.CommonItem
import com.example.presentation.model.ViewObject

sealed class MarvelCharacterViewHolder(
    binding: ViewDataBinding
) : RecyclerView.ViewHolder(binding.root) {

    abstract fun bind(item: CommonItem)

    class LoadingViewHolder(binding: ItemLoadingBinding) : MarvelCharacterViewHolder(binding) {
        override fun bind(item: CommonItem) = Unit
    }

    class EmptyViewHolder(binding: ItemEmptyBinding) : MarvelCharacterViewHolder(binding) {
        override fun bind(item: CommonItem) = Unit
    }

    class SuccessViewHolder(private val binding: ItemMarvelCharacterBinding) : MarvelCharacterViewHolder(binding) {
        override fun bind(item: CommonItem) {
            if (item.viewObject is ViewObject.SuccessViewObject) {
                binding.marvelCharacter = item.viewObject.marvelCharacter
                binding.executePendingBindings()
            }
        }
    }

    class ErrorViewHolder(binding: ItemErrorBinding) : MarvelCharacterViewHolder(binding) {
        override fun bind(item: CommonItem) = Unit
    }

}