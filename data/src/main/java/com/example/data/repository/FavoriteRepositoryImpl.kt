package com.example.data.repository

import com.example.data.local.source.FavoriteLocalDataSource
import com.example.data.repository.model.FavoriteRepositoryModel
import com.example.domain.model.MarvelCharacter
import com.example.domain.repository.FavoriteRepository
import javax.inject.Inject

internal class FavoriteRepositoryImpl @Inject constructor(
    private val favoriteLocalDataSource: FavoriteLocalDataSource
) : FavoriteRepository {

    override suspend fun insertFavorite(marvelCharacter: MarvelCharacter) {
        favoriteLocalDataSource.insertFavorite(
            FavoriteRepositoryModel(
                characterId = marvelCharacter.id,
                name = marvelCharacter.name,
                description = marvelCharacter.description,
                thumbnail = marvelCharacter.thumbnail
            )
        )
    }

    override suspend fun selectId(characterId: String): Boolean {
        return favoriteLocalDataSource.selectId(characterId)
    }

}