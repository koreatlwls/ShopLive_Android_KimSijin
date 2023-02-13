package com.example.presentation.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.presentation.databinding.ItemEmptyBinding
import com.example.presentation.databinding.ItemErrorBinding
import com.example.presentation.databinding.ItemLoadingBinding
import com.example.presentation.databinding.ItemMarvelCharacterBinding
import com.example.presentation.model.CommonItem
import com.example.presentation.model.UiState
import com.example.presentation.model.ViewObject

class MarvelCharacterAdapter(
    private val onItemClick: (Pair<CommonItem, Int>) -> Unit
) : ListAdapter<CommonItem, CommonViewHolder>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommonViewHolder {
        return when (viewType) {
            UiState.Loading.ordinal -> {
                CommonViewHolder.LoadingViewHolder(
                    ItemLoadingBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
            UiState.Empty.ordinal -> {
                CommonViewHolder.EmptyViewHolder(
                    ItemEmptyBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
            UiState.Success.ordinal -> {
                CommonViewHolder.SuccessViewHolder(
                    ItemMarvelCharacterBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
            else -> {
                CommonViewHolder.ErrorViewHolder(
                    ItemErrorBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
        }
    }

    override fun onBindViewHolder(holder: CommonViewHolder, position: Int) {
        holder.bind(currentList[position])
        holder.itemView.setOnClickListener {
            onItemClick.invoke(Pair(currentList[holder.adapterPosition], holder.adapterPosition))
        }
    }

    override fun getItemViewType(position: Int): Int {
        return currentList[position].viewType.ordinal
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<CommonItem>() {

            override fun areItemsTheSame(oldItem: CommonItem, newItem: CommonItem): Boolean {
                return if (oldItem.viewObject is ViewObject.SuccessViewObject && newItem.viewObject is ViewObject.SuccessViewObject) {
                    oldItem.viewObject.marvelCharacter.id == newItem.viewObject.marvelCharacter.id
                } else {
                    oldItem.viewType == newItem.viewType
                }
            }

            override fun areContentsTheSame(oldItem: CommonItem, newItem: CommonItem): Boolean {
                return oldItem == newItem
            }

        }
    }

}