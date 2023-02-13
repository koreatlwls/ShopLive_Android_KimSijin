package com.example.domain.model

data class MarvelCharacter(
    val id: String,
    val name: String,
    val description: String,
    val thumbnail: String,
    val isFavorite: Boolean,
)