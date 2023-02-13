package com.example.data.repository

import com.example.data.local.source.FavoriteLocalDataSource
import com.example.data.repository.model.FavoriteRepositoryModel
import com.example.domain.model.MarvelCharacter
import com.example.domain.repository.FavoriteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
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

    override suspend fun deleteFavoriteWithId(characterId: String) {
        favoriteLocalDataSource.deleteFavoriteWithId(characterId)
    }

    override fun getAllFavorites(): Flow<List<MarvelCharacter>> {
        return favoriteLocalDataSource.getAllFavorites().map { favoriteRepositoryModels ->
            favoriteRepositoryModels.map { favoriteRepositoryModel ->
                favoriteRepositoryModel.toUseCaseModel(true)
            }
        }
    }

}