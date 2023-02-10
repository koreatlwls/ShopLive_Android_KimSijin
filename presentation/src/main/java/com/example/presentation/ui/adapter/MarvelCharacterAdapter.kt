package com.example.presentation.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.model.MarvelCharacter
import com.example.presentation.databinding.ItemMarvelCharacterBinding

class MarvelCharacterAdapter(
    private val onItemClick: (MarvelCharacter) -> Unit
) : ListAdapter<MarvelCharacter, MarvelCharacterAdapter.ViewHolder>(diffUtil) {

    class ViewHolder(private val binding: ItemMarvelCharacterBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(marvelCharacter: MarvelCharacter) {
            binding.marvelCharacter = marvelCharacter
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemMarvelCharacterBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList[position])
        holder.itemView.setOnClickListener {
            onItemClick.invoke(currentList[holder.adapterPosition])
        }
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<MarvelCharacter>() {

            override fun areItemsTheSame(oldItem: MarvelCharacter, newItem: MarvelCharacter): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: MarvelCharacter, newItem: MarvelCharacter): Boolean {
                return oldItem == newItem
            }

        }
    }

}