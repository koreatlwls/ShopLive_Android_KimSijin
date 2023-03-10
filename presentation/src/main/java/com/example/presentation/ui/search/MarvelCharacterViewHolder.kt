package com.example.presentation.ui.search

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.model.MarvelCharacter
import com.example.presentation.R
import com.example.presentation.databinding.ItemEmptyBinding
import com.example.presentation.databinding.ItemErrorBinding
import com.example.presentation.databinding.ItemLoadingBinding
import com.example.presentation.databinding.ItemMarvelCharacterBinding
import com.example.presentation.model.CommonItem
import com.example.presentation.model.UiState
import com.example.presentation.model.ViewObject

sealed class MarvelCharacterViewHolder(
    binding: ViewDataBinding
) : RecyclerView.ViewHolder(binding.root) {

    var onItemClick: ((CommonItem) -> Unit)? = null

    abstract fun bind(item: CommonItem)

    class LoadingViewHolder(binding: ItemLoadingBinding) : MarvelCharacterViewHolder(binding) {
        override fun bind(item: CommonItem) = Unit
    }

    class EmptyViewHolder(binding: ItemEmptyBinding) : MarvelCharacterViewHolder(binding) {
        override fun bind(item: CommonItem) = Unit
    }

    class SuccessViewHolder(private val binding: ItemMarvelCharacterBinding) : MarvelCharacterViewHolder(binding) {

        private var lastTime = System.currentTimeMillis()
        private lateinit var commonItem: CommonItem

        override fun bind(item: CommonItem) {
            commonItem = item

            binding.root.setOnClickListener {
                val currentTime = System.currentTimeMillis()
                if (currentTime - lastTime > CLICK_DURATION) {
                    lastTime = currentTime
                    onItemClick?.invoke(commonItem)
                }
            }

            if (item.viewObject is ViewObject.SuccessViewObject) {
                binding.marvelCharacter = item.viewObject.marvelCharacter
                binding.executePendingBindings()
            }
        }

        fun bindFavorite(marvelCharacter: MarvelCharacter) {
            commonItem = CommonItem(
                UiState.Success,
                ViewObject.SuccessViewObject(marvelCharacter)
            )

            if (marvelCharacter.isFavorite) {
                binding.cardView.setBackgroundResource(R.color.light_gray)
            } else {
                binding.cardView.setBackgroundResource(R.color.white)
            }
        }
    }

    class ErrorViewHolder(private val binding: ItemErrorBinding) : MarvelCharacterViewHolder(binding) {
        override fun bind(item: CommonItem) {
            binding.retryButton.setOnClickListener {
                onItemClick?.invoke(item)
            }
        }
    }

    companion object {
        private const val CLICK_DURATION = 1000L
    }

}