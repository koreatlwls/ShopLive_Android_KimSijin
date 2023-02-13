package com.example.data.repository.model

import com.example.data.local.model.FavoriteEntity
import com.example.domain.model.MarvelCharacter

internal data class FavoriteRepositoryModel(
    val characterId: String,
    val name: String,
    val description: String,
    val thumbnail: String
) {

    fun toEntity() = FavoriteEntity(
        characterId = characterId,
        name = name,
        description = description,
        thumbnail = thumbnail
    )

    fun toUseCaseModel(check: Boolean) = MarvelCharacter(
        id = characterId,
        name = name,
        description = description,
        thumbnail = thumbnail,
        isFavorite = check
    )

}
