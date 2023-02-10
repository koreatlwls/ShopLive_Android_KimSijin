package com.example.data.repository.model

import com.example.domain.model.MarvelCharacter

internal data class MarvelCharacterRepositoryModel(
    val id: String,
    val name: String,
    val description: String,
    val thumbnail: String
) {
    fun toUseCaseModel() = MarvelCharacter(
        id = id,
        name = name,
        description = description,
        thumbnail = thumbnail,
    )
}
